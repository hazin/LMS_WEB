package com.gcit.lms.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookLoan;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.LibraryBranch;
import com.gcit.lms.service.AdminService;
import com.gcit.lms.service.BorrowerService;

/**
 * Servlet implementation class BorrowerServlet
 */
@WebServlet({"/logIn", "/viewAvailBooks", "/loanBook", "/loanedBooks", "/returnBook", "/returnReceipt"})
public class BorrowerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	public BorrowerServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String function = request.getRequestURI().substring(
				request.getContextPath().length(),
				request.getRequestURI().length());

		// System.out.println(function);

		switch (function) {
		case "/logIn": {
			try {
				logIn(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		case "/viewAvailBooks": {
			try {
				viewAvailBooks(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		case "/loanBook": {
			try {
				loanBook(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		case "/loanedBooks": {
			try {
				loanedBook(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		case "/returnBook": {
			try {
				returnBook(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		default:
			break;
		}
	}

	private void returnBook(HttpServletRequest request,	HttpServletResponse response) throws Exception {
		int cardNo = Integer.parseInt(request.getParameter("cardNo"));
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		BookLoan loanedBook = new BookLoan();	
		
		BorrowerService borrowerServe = new BorrowerService();
		Book book = borrowerServe.getBookById(bookId);
		Borrower borrower = borrowerServe.getBorrower(cardNo);
		
		loanedBook.setBook(book);
		loanedBook.setBorrower(borrower);
		loanedBook.setBranch(borrowerServe.getLoaningBranch(loanedBook));
		
		borrowerServe.returnBook(loanedBook);		
		loanedBook = borrowerServe.completeRecord(loanedBook);
		
		request.setAttribute("loanedBooks", loanedBook);
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/Borrower/returnReceipt.jsp");

		rd.forward(request, response);
	}

	private void loanedBook(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int cardNo = Integer.parseInt(request.getParameter("cardNo"));
		
		BorrowerService borrowerServe = new BorrowerService();
		Borrower borrower = borrowerServe.getBorrower(cardNo);
		BookLoan loanedBook = new BookLoan();	
		
		loanedBook.setBorrower(borrower);		
		List<Book> book = borrowerServe.getLoanedBook(loanedBook);		
		
		request.setAttribute("loanedBooks", book);
		request.setAttribute("borrower", borrower);
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/Borrower/returnBook.jsp");

		rd.forward(request, response);
	}

	private void loanBook(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		int branchId = Integer.parseInt(request.getParameter("loanBranchId"));
		int cardNo = Integer.parseInt(request.getParameter("cardNo"));
		int bookLoanId = Integer.parseInt(request.getParameter("bookLoanId"));
		
		BorrowerService borrowerServe = new BorrowerService();		
		AdminService adminService = new AdminService();
		
		Book book = borrowerServe.getBookById(bookLoanId);
		book = adminService.completeBook(book);
		
		LibraryBranch branch = borrowerServe.getBranchById(branchId);
		Borrower borrower = borrowerServe.getBorrower(cardNo);
		
		BookLoan loanedBook = new BookLoan();	
		
		loanedBook.setBranch(branch);
		loanedBook.setBorrower(borrower);
		loanedBook.setBook(book);
		
		borrowerServe.insertLoan(loanedBook);
		
		request.setAttribute("receipt",loanedBook);
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/Borrower/receipt.jsp");

		rd.forward(request, response);
	}

	private void viewAvailBooks(HttpServletRequest request,	HttpServletResponse response) throws Exception {
		
		int branchId = Integer.parseInt(request.getParameter("branchViewId"));
		int cardNo = Integer.parseInt(request.getParameter("cardNo"));
		BorrowerService borrowerServe = new BorrowerService();		
		
		LibraryBranch branch = borrowerServe.getBranchById(branchId);
		
		List<Book> authorBookList = new ArrayList<Book>();
		authorBookList = borrowerServe.getBooks(branchId);
		
		Borrower borrower = borrowerServe.getBorrower(cardNo);
		
		request.setAttribute("borrower",borrower);
		request.setAttribute("branch",branch);
		request.setAttribute("booklist",authorBookList);
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/Borrower/listBooks.jsp");

		rd.forward(request, response);
		
	}

	private void logIn(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int cardNo = Integer.parseInt(request.getParameter("cardNo"));
		BorrowerService borrowServe = new BorrowerService();
		
		if (borrowServe.checkCardNo(cardNo)){
			
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/Borrower/borrower.jsp");
			
			Borrower borrower = borrowServe.getBorrower(cardNo);
			request.setAttribute("borrower",borrower);

			rd.forward(request, response);
			
		}
		else {
			request.setAttribute("result", "Card number incorrect, please enter a valid card number");
			
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/Borrower/logIn.jsp");

			rd.forward(request, response);
		}
	}
}