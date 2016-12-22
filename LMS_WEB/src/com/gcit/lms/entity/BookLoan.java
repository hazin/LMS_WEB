package com.gcit.lms.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class BookLoan{// implements Serializable{

	
	private int bookLoanID;
	private Book book;
	private LibraryBranch branch;	
	private Borrower borrower;
	
	private String dateOut;
	private String dateIn;
	private String dueDate;
	
	public int getBookLoanID() {
		return bookLoanID;
	}
	public void setBookLoanID(int bookLoanID) {
		this.bookLoanID = bookLoanID;
	}
	
	public String getDateOut() {
		return dateOut;
	}
	public void setDateOut(String dateOut) {
		this.dateOut = dateOut;
	}
	
	public String getDateIn() {
		return dateIn;
	}
	public void setDateIn(String dateIn) {
		this.dateIn = dateIn;
	}
	
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	
	public LibraryBranch getBranch() {
		return branch;
	}
	public void setBranch(LibraryBranch branch) {
		this.branch = branch;
	}
	
	public Borrower getBorrower() {
		return borrower;
	}
	public void setBorrower(Borrower borrower) {
		this.borrower = borrower;	
	}
}