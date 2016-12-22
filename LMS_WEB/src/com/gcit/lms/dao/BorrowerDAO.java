package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Borrower;

public class BorrowerDAO extends BaseDAO<List<Borrower>> {

	public BorrowerDAO(Connection conn) {
		super(conn);
	}

	public void create(Borrower borrower) throws Exception {
		save("insert into tbl_borrower (tbl_borrower.name, tbl_borrower.address, tbl_borrower.phone) "
				+ "values (?,?,?)", new Object[] { borrower.getBorrowerName(),
				borrower.getBorrowerAddress(), borrower.getBorrowerPhone() });
	}

	public void update(Borrower borrower) throws Exception {
		save("update tbl_borrower set tbl_borrower.name = ?, tbl_borrower.address = ?, "
				+ "tbl_borrower.phone = ? where tbl_borrower.cardNo = ?",
				new Object[] { borrower.getBorrowerName(),
						borrower.getBorrowerAddress(),
						borrower.getBorrowerPhone(), borrower.getCardNo() });
	}

	public void delete(Borrower borrower) throws Exception {
		save("delete from tbl_borrower where tbl_borrower.cardNo = ?",
				new Object[] { borrower.getCardNo() });
	}

	@SuppressWarnings("unchecked")
	public List<Borrower> readAll() throws Exception {
		return (List<Borrower>) read("select * from tbl_borrower", null);
	}

	@SuppressWarnings("unchecked")
	public Borrower readOne(int borrowerId) throws Exception {
		List<Borrower> borrowerList = (List<Borrower>) read(
				"select * from tbl_borrower where cardNo = ?",
				new Object[] { borrowerId });

		if (borrowerList != null && borrowerList.size() > 0) {
			return borrowerList.get(0);
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Borrower readOne(String borrowerName) throws Exception {
		List<Borrower> list = (List<Borrower>) read(
				"select * from tbl_borrower where name = ?",
				new Object[] { borrowerName });

		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}	
	

	@Override
	protected List<Borrower> extractData(ResultSet rs) throws SQLException {
		List<Borrower> pubList = new ArrayList<Borrower>();
		while (rs.next()) {
			Borrower borrower = new Borrower();
			borrower.setCardNo(rs.getInt("cardNo"));
			borrower.setBorrowerName(rs.getString("name"));
			borrower.setBorrowerAddress(rs.getString("address"));
			borrower.setBorrowerPhone(rs.getString("phone"));

			pubList.add(borrower);
		}
		return pubList;
	}

}