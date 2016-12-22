package com.gcit.lms.entity;

import java.util.List;

public class Borrower {
	
	private String borrowerName;
	private String borrowerAddress;
	private String borrowerPhone;
	
	private int cardNo;
	private List<Book> books;
	/**
	 * @return the borrowerName
	 */
	public String getBorrowerName() {
		return borrowerName;
	}
	/**
	 * @param borrowerName the borrowerName to set
	 */
	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}
	/**
	 * @return the borrowerAddress
	 */
	public String getBorrowerAddress() {
		return borrowerAddress;
	}
	/**
	 * @param borrowerAddress the borrowerAddress to set
	 */
	public void setBorrowerAddress(String borrowerAddress) {
		this.borrowerAddress = borrowerAddress;
	}
	/**
	 * @return the borrowerPhone
	 */
	public String getBorrowerPhone() {
		return borrowerPhone;
	}
	/**
	 * @param borrowerPhone the borrowerPhone to set
	 */
	public void setBorrowerPhone(String borrowerPhone) {
		this.borrowerPhone = borrowerPhone;
	}
	/**
	 * @return the cardNo
	 */
	public int getCardNo() {
		return cardNo;
	}
	/**
	 * @param cardNo the cardNo to set
	 */
	public void setCardNo(int cardNo) {
		this.cardNo = cardNo;
	}
	/**
	 * @return the books
	 */
	public List<Book> getBooks() {
		return books;
	}
	/**
	 * @param books the books to set
	 */
	public void setBooks(List<Book> books) {
		this.books = books;
	}

	

}
