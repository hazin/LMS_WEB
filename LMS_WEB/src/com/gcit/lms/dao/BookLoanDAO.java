package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.BookLoan;

public class BookLoanDAO extends BaseDAO<List<BookLoan>> {

	public BookLoanDAO(Connection conn) {
		super(conn);
	}

	public void create(BookLoan bookloan) throws Exception {
		save("insert into tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate) "
				+ "values (?, ?, ?, NOW() , DATE_ADD(NOW(), INTERVAL 7 DAY))",
				new Object[] { bookloan.getBook().getBookId(),
						bookloan.getBranch().getBranchId(),
						bookloan.getBorrower().getCardNo() });
	}

	// Update table when book is returned set return date
	public void update(BookLoan bookloan) throws Exception {
		save("update tbl_book_loans set dateIn = NOW() where bookId = ? and branchId = ?",
				new Object[] { bookloan.getBook().getBookId(),
						bookloan.getBranch().getBranchId() });
	}

	// Override due date
	public void updateDueDate(BookLoan bookloan) throws Exception {
		save("update tbl_book_loans set dueDate = ? where bookLoanId = ?",
				new Object[] { bookloan.getDueDate(), bookloan.getBookLoanID() });
	}

	// delete book loan record
	public void delete(BookLoan bookloan) throws Exception {
		save("delete from tbl_book_loans where bookLoanId = ?",
				new Object[] { bookloan.getBookLoanID() });
	}

	// Get all records in book loan table
	@SuppressWarnings("unchecked")
	public List<BookLoan> readAll() throws Exception {
		return (List<BookLoan>) read("select * from tbl_book_loans", null);
	}

	@SuppressWarnings("unchecked")
	public List<BookLoan> readAll(BookLoan bookLoaned) throws Exception {
		return (List<BookLoan>) read(
				"select * from tbl_book_loans where cardNo = ?",
				new Object[] { bookLoaned.getBorrower().getCardNo() });
	}

	// Get record in book loan table based ont book loan ID
	@SuppressWarnings("unchecked")
	public BookLoan readOne(BookLoan bookLoaned) throws Exception {
		List<BookLoan> list = (List<BookLoan>) read(
				"select * from tbl_book_loans where bookId = ? and cardNo = ?",
				new Object[] { bookLoaned.getBook().getBookId(),
						bookLoaned.getBorrower().getCardNo() });

		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	// Get record in book loan table based ont book loan ID
	@SuppressWarnings("unchecked")
	public BookLoan readOneByBranch(BookLoan bookLoaned) throws Exception {
		List<BookLoan> list = (List<BookLoan>) read(
				"select * from tbl_book_loans where (bookId = ? and cardNo = ?) and branchId = ?",
				new Object[] { bookLoaned.getBook().getBookId(),
						bookLoaned.getBorrower().getCardNo(),
						bookLoaned.getBranch().getBranchId() });

		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	// Get record in bool loan table based on book loan ID
	@SuppressWarnings("unchecked")
	public BookLoan readOne(int branchId, int cardNo) throws Exception {
		List<BookLoan> list = (List<BookLoan>) read(
				"select * from tbl_book_loans where branchId = ? and cardNo = ?",
				new Object[] { branchId });

		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	// Get record in bool loan table based on book loan ID
	@Override
	protected List<BookLoan> extractData(ResultSet rs) throws SQLException {
		List<BookLoan> list = new ArrayList<BookLoan>();
		while (rs.next()) {
			BookLoan bkL = new BookLoan();
			LibraryBranchDAO branchManager = new LibraryBranchDAO(super.getConnection());
			BookDAO bookManager = new BookDAO(super.getConnection());
			BorrowerDAO borrowerManager = new BorrowerDAO(super.getConnection());

			try {
				bkL.setBranch(branchManager.readOne(rs.getInt("branchId")));
				bkL.setBook(bookManager.readOne(rs.getInt("bookId")));
				bkL.setBorrower(borrowerManager.readOne(rs.getInt("cardNo")));

				bkL.setDateOut(rs.getString("dateOut"));
				bkL.setDueDate(rs.getString("dueDate"));
				bkL.setDateIn(rs.getString("dateIn"));

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			list.add(bkL);
		}
		return list;
	}
}