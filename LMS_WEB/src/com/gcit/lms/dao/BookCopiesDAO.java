package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.BookCopies;

public class BookCopiesDAO  extends BaseDAO<List<BookCopies>> {

	public BookCopiesDAO(Connection conn) {
		super(conn);
	}
	
	public void insert(BookCopies bkCopy) throws Exception {
		save("insert into tbl_book_copies (bookId, branchId, noOfCopies) values (?, ?, ?)",
				new Object[] { bkCopy.getBook().getBookId(), bkCopy.getBranch().getBranchId(), bkCopy.getNoOfCopies() });
	}

	public void AddtoCopies(int bookId, int branchId) throws Exception{
		save("UPDATE tbl_book_copies SET noOfCopies = noOfCopies + 1 WHERE bookId = ? and branchId = ?",
				new Object[] { bookId, branchId });
	}
	
	public void MinusFromCopies(int bookId, int branchId) throws Exception{
		save("UPDATE tbl_book_copies SET noOfCopies = noOfCopies - 1 WHERE bookId = ? and branchId = ?",
				new Object[] { bookId, branchId });
	}
	
	
	public void update(BookCopies bkCopy) throws Exception {
		save("update tbl_book_copies set noOfCopies = ? where branchId = ? and bookId = ?",
				new Object[] { bkCopy.getNoOfCopies(), bkCopy.getBranch().getBranchId(), bkCopy.getBook().getBookId()});
	}

	@SuppressWarnings("unchecked")
	public List<BookCopies> readAll() throws Exception {
		return (List<BookCopies>) read("select * from tbl_book_copies", null);
	}
	
	@SuppressWarnings("unchecked")
	public BookCopies readOne(BookCopies bkCopy) throws Exception {
		List<BookCopies> list = (List<BookCopies>) read(
				"select * from tbl_book_copies where branchId = ? and bookId = ?",
				new Object[] { bkCopy.getBranch().getBranchId(), bkCopy.getBook().getBookId() });

		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	
	// Gets list of items in table and creates a list of them setting their 
	// properties with values from the database
	@Override
	protected List<BookCopies> extractData(ResultSet rs) throws SQLException {
		List<BookCopies> list = new ArrayList<BookCopies>();
		while (rs.next()) {
			LibraryBranchDAO branchManager = new LibraryBranchDAO(super.getConnection());
			BookDAO bookManager = new BookDAO(super.getConnection());			
			BookCopies bkC;
			
			try {
				bkC = new BookCopies();
				bkC.setBranch(branchManager.readOne(rs.getInt("branchId")));
				bkC.setBook(bookManager.readOne(rs.getInt("bookId")));
				bkC.setNoOfCopies(rs.getInt("noOfCopies"));
				
				list.add(bkC);
			} catch (Exception e) {
				// TODO: handle exception
			}			
		}
		return list;
	}

}