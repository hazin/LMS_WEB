package com.gcit.lms.entity;

import java.io.Serializable;
import java.sql.Connection;

import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.service.ConnectionUtil;

public class BookCopies implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8698462912587446557L;
	
	private Book book;
	private LibraryBranch branch;
	private int noOfCopies;
	/**
	 * @return the book
	 */
	public Book getBook() {
		return book;
	}
	/**
	 * @param book the book to set
	 */
	public void setBook(Book book) {
		this.book = book;
	}
	/**
	 * @return the branch
	 */
	public LibraryBranch getBranch() {
		return branch;
	}
	/**
	 * @param branch the branch to set
	 */
	public void setBranch(LibraryBranch branch) {
		this.branch = branch;
	}
	/**
	 * @return the noOfCopies
	 */
	public int getNoOfCopies() {
		return noOfCopies;
	}
	/**
	 * @param noOfCopies the noOfCopies to set
	 */
	public void setNoOfCopies(int noOfCopies) {
		this.noOfCopies = noOfCopies;
	}

	
}
