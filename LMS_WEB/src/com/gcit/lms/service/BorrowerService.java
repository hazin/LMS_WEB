package com.gcit.lms.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookLoanDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.BookLoan;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.LibraryBranch;

public class BorrowerService {

		public void getCurrBookCopies(BookCopies bkCopy) throws Exception {

			Connection c = ConnectionUtil.getConnection();
			try {
				BookCopiesDAO bookCopiesManager = new BookCopiesDAO(c);
				bkCopy = bookCopiesManager.readOne(bkCopy);

				System.out.println();
				System.out.println("Existing number of copies: "
						+ bkCopy.getNoOfCopies());

			} finally {
				c.close();
			}
		}

		public Boolean checkCardNo(int cardNo) throws Exception {
			
			Connection c = ConnectionUtil.getConnection();
			try {
			Borrower borrower = new Borrower();
			BorrowerDAO borrowerManager = new BorrowerDAO (c);
			
			borrower = borrowerManager.readOne(cardNo);
			
			if (borrower != null)
				return true;
			else 
				return false;	

			} finally {
				c.close();
			}

		}
		
		public Borrower getBorrower(int cardNo) throws Exception {
			Connection c = ConnectionUtil.getConnection();
			BorrowerDAO borrowerManager = new BorrowerDAO(c);
			
			Borrower b = borrowerManager.readOne(cardNo);
			c.close();
						
			return b;	
		}
		
		public void getAllBranches() throws Exception {

			Connection c = ConnectionUtil.getConnection();
			LibraryBranchDAO branchManager = new LibraryBranchDAO(c);

			List<LibraryBranch> branchList = (List<LibraryBranch>) branchManager.readAll();

			System.out.println();
			for (LibraryBranch b : branchList) {
				System.out.println(b.getBranchName() + ", " + b.getBranchAddress());
			}

			c.close();
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
		
		public LibraryBranch getLoaningBranch(BookLoan bookLoaned) throws Exception {
			Connection c = ConnectionUtil.getConnection();
			try {			
				BookLoanDAO loansManager = new BookLoanDAO(c);
				bookLoaned = loansManager.readOne(bookLoaned);			
				
				return bookLoaned.getBranch();
			} finally {
				c.close();
			}
		}
		
		public List<Book> getBooks(int branchId) throws Exception {
			Connection c = ConnectionUtil.getConnection();
			try {
				List<Book> authorBookList = new ArrayList<Book>();

				BookDAO bookCopyAuthor = new BookDAO(c);
				authorBookList = bookCopyAuthor.readAllInBranch(branchId);
				
				return authorBookList;
				
				/*System.out.println();
				System.out.println("Available Books:");
				for (Book booklist : authorBookList) {
					System.out.println(booklist.getTitle() + " by "
							+ booklist.getAuthors().get(0).getAuthorName());
				}*/
				
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
		
		// Display loaned books
		public List<Book> getLoanedBook(BookLoan b) throws Exception {
			Connection c = ConnectionUtil.getConnection();
			try {			
				List<Book> loanedBook = new ArrayList<Book>();
				BookDAO loanedBkTitle = new BookDAO(c);
							 
				loanedBook = loanedBkTitle.readAllLoanedWithAuthor( b.getBorrower().getCardNo());			
				return loanedBook;
				
			} finally {
				c.close();
			}
		}
		
		public void returnBook(BookLoan loanedBook) throws Exception{
			Connection c = ConnectionUtil.getConnection();
			try {
				BookCopiesDAO bookCopiesMan = new BookCopiesDAO(c);
				
				BookLoanDAO bookLoansManager = new BookLoanDAO(c);
				bookLoansManager.update(loanedBook);
				
				// Add to copies 
				bookCopiesMan.AddtoCopies(loanedBook.getBook().getBookId(), loanedBook.getBranch().getBranchId());
				c.commit();
				
				System.out.println();
				System.out.println("Update successful.");
				System.out.println();

			} catch (Exception e) {
				c.rollback();
				throw e;
			} finally {
				c.close();
			}
			
		}
		
		public void insertLoan(BookLoan loanedBook) throws Exception {

			Connection c = ConnectionUtil.getConnection();
			try {
				BookLoanDAO bookCopiesManager = new BookLoanDAO(c);			
				bookCopiesManager.create(loanedBook);
				
				BookCopiesDAO bookCopiesMan = new BookCopiesDAO(c);
				bookCopiesMan.MinusFromCopies(loanedBook.getBook().getBookId(), loanedBook.getBranch().getBranchId());
				
				c.commit();
				
				System.out.println();
				System.out.println("Update successful.");
				System.out.println();

			} catch (Exception e) {
				c.rollback();
				throw e;
			} finally {
				c.close();
			}
		}

		public LibraryBranch getBranchById (int branchId) throws Exception {
			Connection c = ConnectionUtil.getConnection();
			try {
				LibraryBranchDAO branchManager = new LibraryBranchDAO(c);
				return branchManager.readOne(branchId);
			} finally {
				c.close();
			}
		}

		public Book getBookById(int bookId) throws Exception {
			Connection c = ConnectionUtil.getConnection();
			try {
				BookDAO bookManager = new BookDAO(c);
				return bookManager.readOne(bookId);
			} finally {
				c.close();
			}
		}
		
		public BookLoan completeRecord (BookLoan loanedBook) throws Exception{
			Connection c = ConnectionUtil.getConnection();
			
			
			BookLoanDAO bookCopiesManager = new BookLoanDAO(c);			
			BookLoan compRecord = bookCopiesManager.readOne(loanedBook);
			c.close();	
			
			return compRecord;
		}
	}
