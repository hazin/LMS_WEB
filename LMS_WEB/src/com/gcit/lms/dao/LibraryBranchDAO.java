package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.LibraryBranch;

public class LibraryBranchDAO extends BaseDAO<List<LibraryBranch>> {

	public LibraryBranchDAO(Connection conn) {
		super(conn);
	}

	public void create(LibraryBranch branch) throws Exception {
		save("INSERT INTO tbl_library_branch"
				+ " (tbl_library_branch.branchName, tbl_library_branch.branchAddress)"
				+ " VALUES (?, ?)", new Object[] { branch.getBranchName(),
				branch.getBranchAddress() });
	}

	public void update(LibraryBranch branch) throws Exception {
		save("UPDATE tbl_library_branch SET branchName = ?, branchAddress = ?"
				+ "WHERE branchId = ?", new Object[] { branch.getBranchName(),
				branch.getBranchAddress(), branch.getBranchId() });
	}

	public void delete(LibraryBranch branch) throws Exception {

		/*
		 * Delete entry from book copies table before branch is deleted
		 */
		save("DELETE FROM tbl_book_copies WHERE tbl_book_copies.branchId = ?",
				new Object[] { branch.getBranchId() });

		// Delete branch from library branch table
		save("DELETE FROM tbl_library_branch WHERE tbl_library_branch.branchId = ?",
				new Object[] { branch.getBranchId() });
	}

	@SuppressWarnings("unchecked")
	public List<LibraryBranch> readAll() throws Exception {
		return (List<LibraryBranch>) read("SELECT * FROM tbl_library_branch", null);
	}

	// Print single record from table
	@SuppressWarnings("unchecked")
	public LibraryBranch readOne(int branchId) throws Exception {
		List<LibraryBranch> branchList = (List<LibraryBranch>) read(
				"SELECT * FROM tbl_library_branch WHERE tbl_library_branch.branchId = ?",
				new Object[] { branchId });

		if (branchList != null && branchList.size() > 0) {
			return branchList.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public LibraryBranch readOne(String branchName) throws Exception {
		List<LibraryBranch> list = (List<LibraryBranch>) read(
				"select * from tbl_library_branch where branchName = ?",
				new Object[] { branchName });

		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	// Print table contents
	@Override
	protected List<LibraryBranch> extractData(ResultSet rs) throws SQLException {
		List<LibraryBranch> branchList = new ArrayList<LibraryBranch>();
		while (rs.next()) {
			LibraryBranch b = new LibraryBranch();
			b.setBranchId(rs.getInt("branchId"));
			b.setBranchName(rs.getString("branchName"));
			b.setBranchAddress(rs.getString("branchAddress"));

			branchList.add(b);
		}
		return branchList;
	}

}