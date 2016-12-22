package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.LibraryBranch;

public class LibrarianService {

	/*Branch Funcions*/
	public List<LibraryBranch> getAllBranches() throws Exception {

		Connection c = ConnectionUtil.getConnection();
		LibraryBranchDAO branchManager = new LibraryBranchDAO(c);

		List<LibraryBranch> branchList = (List<LibraryBranch>) branchManager.readAll();
		c.close();
		
		return branchList;
	}

	public LibraryBranch getBranchByName(String branchName) throws Exception {
		Connection c = ConnectionUtil.getConnection();
		try {
			LibraryBranchDAO branchManager = new LibraryBranchDAO(c);
			return branchManager.readOne(branchName);
		} finally {
			c.close();
		}
	}
	
	public LibraryBranch getBranchById(int branchId) throws Exception {
		Connection c = ConnectionUtil.getConnection();
		try {
			LibraryBranchDAO branchManager = new LibraryBranchDAO(c);
			return branchManager.readOne(branchId);
		} finally {
			c.close();
		}
	}

	public void updateLibDetails(LibraryBranch branch) throws ClassNotFoundException,
			SQLException {

		Connection c = ConnectionUtil.getConnection();
		LibraryBranchDAO branchManager = new LibraryBranchDAO(c);

		try {
			branchManager.update(branch);
			branch = branchManager.readOne(branch.getBranchName());
			
			c.commit();

		} catch (Exception e) {
			c.rollback();
		} finally {
			c.close();
		}
	}

	public Book getBookByName(String bookname) throws Exception {
		Connection c = ConnectionUtil.getConnection();
		try {
			BookDAO bookManager = new BookDAO(c);
			return bookManager.readOne(bookname);
		} finally {
			c.close();
		}
	}
	
	public Book getBookById(int bookid) throws Exception {
		Connection c = ConnectionUtil.getConnection();
		try {
			BookDAO bDAO = new BookDAO(c);
			return bDAO.readOne(bookid);
		} finally {
			c.close();
		}
	}

	public List<Book> getBooks() throws Exception {
		Connection c = ConnectionUtil.getConnection();
		try {
			List<Book> authorBookList = new ArrayList<Book>();

			BookDAO bookCopyAuthor = new BookDAO(c);
			authorBookList = bookCopyAuthor.readAllWithAuthor();
			
			return authorBookList;
		} finally {
			c.close();
		}
	}
	
	/*public void getBookcopies(int bookId, int branchId) throws Exception {
		Connection c = ConnectionUtil.getConnection();
		try {
			BookCopies bookCopiesList = new BookCopies();

			BookCopiesDAO bkCopies = new BookCopiesDAO(c);
			BookCopies bk = new BookCopies();
	
			bkCopies.update(bk);
			
		} finally {
			c.close();
		}
	}*/
	
	

	public int getCurrBookCopies(BookCopies bkCopy) throws Exception {

		Connection c = ConnectionUtil.getConnection();
		try {
			BookCopiesDAO bookCopiesManager = new BookCopiesDAO(c);
			bkCopy = bookCopiesManager.readOne(bkCopy);

			if (bkCopy != null) {
				return bkCopy.getNoOfCopies();
			} else {
				return -1;
			}

		} finally {
			c.close();
		}
	}

	public void updateCopies(BookCopies bkCopy) throws Exception {

		Connection c = ConnectionUtil.getConnection();
		try {
			BookCopiesDAO bookCopiesManager = new BookCopiesDAO(c);
			bookCopiesManager.update(bkCopy);
			c.commit();
			
		} catch (Exception e) {
			c.rollback();
			throw e;
		} finally {
			c.close();
		}
	}

	public void insertCopies(BookCopies bkCopy) throws Exception {

		Connection c = ConnectionUtil.getConnection();
		try {
			BookCopiesDAO bookCopiesManager = new BookCopiesDAO(c);
			bookCopiesManager.insert(bkCopy);
			c.commit();

		} catch (Exception e) {
			c.rollback();
			throw e;
		} finally {
			c.close();
		}
	}
	
}