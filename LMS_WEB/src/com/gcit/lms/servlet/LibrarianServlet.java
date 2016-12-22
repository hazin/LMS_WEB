package com.gcit.lms.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.LibraryBranch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.gcit.lms.service.AdminService;
import com.gcit.lms.service.LibrarianService;

/**
 * Servlet implementation class AdministratorServlet
 */
@WebServlet({"/viewLibBooks", "/editNoOfCopies", "/editLibBranch"})
public class LibrarianServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LibrarianServlet() {
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

		System.out.println(function);

		switch (function) {
		case "/viewLibBooks": {
			try {
				viewLibBooks(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		case "/editNoOfCopies": {
			try {
				editNoOfCopies(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		case "/editLibBranch": {
			try {
				editBranch(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		default:
			break;
		}
	}
	
	// Update number of copies of book in branch
	private void editNoOfCopies(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		int branchId = Integer.parseInt(request.getParameter("branchId"));
		int noOfCopies = Integer.parseInt(request.getParameter("noOfCopies"));
		System.out.println(bookId);
		System.out.println(noOfCopies);
		System.out.println(branchId);
		
		BookCopies bookCopy = new BookCopies();
		LibraryBranch branch = new LibraryBranch();
		Book book = new Book();
		
		LibrarianService libServe = new LibrarianService();
		book = libServe.getBookById(bookId);
		branch = libServe.getBranchById(branchId);
		bookCopy.setBook(book);
		bookCopy.setBranch(branch);
		

		RequestDispatcher rd = getServletContext().getRequestDispatcher("/editNoOfCopies.jsp");
		request.setAttribute("libServe", libServe);
		rd.forward(request, response);
		
		/*
		 * RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/detailedBook.jsp");

		request.setAttribute("CompleteBook", book);

		rd.forward(request, response);
		 * */
		
		/*noOfCopies = libServe.getCurrBookCopies(bookCopy);
		bookCopy.setNoOfCopies(noOfCopies);

		if (noOfCopies == -1)
			libServe.insertCopies(bookCopy);
		else
			libServe.updateCopies(bookCopy);
		
		goToBranchPage(request, response, branchId);*/
	}

	// view book copies in a given library
	private void viewLibBooks(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String branchId = request.getParameter("branchViewId");		
		
		goToBranchPage(request, response, Integer.parseInt(branchId));
	}
	
	// Update branch info
	private void editBranch(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String branchName = request.getParameter("branchName");
		String branchAddress = request.getParameter("branchAddress");
		String branchId = request.getParameter("branchUpdateId");
		
		LibraryBranch branch = new LibraryBranch();
		
		branch.setBranchName(branchName);
		branch.setBranchAddress(branchAddress);
		branch.setBranchId(Integer.parseInt(branchId));	
			
		AdminService admin = new AdminService();
		admin.updateBranch(branch);
		
		goToBranchPage(request, response, Integer.parseInt(branchId));		
	}
	
	// Redirect to branch page
	private void goToBranchPage(HttpServletRequest request,
			HttpServletResponse response, int branchId) throws Exception,
			ServletException, IOException {
		
		LibraryBranch branch = new LibraryBranch();
		List<BookCopies> bookCopyList = new ArrayList<BookCopies>();
		BookCopies bookCopy = new BookCopies();
		
		LibrarianService libServe = new LibrarianService();
		List<Book> authorBookList = new ArrayList<Book>();
		
		branch = libServe.getBranchById(branchId);
		authorBookList = libServe.getBooks();
		
		for (Book b :authorBookList){
			bookCopy = new BookCopies();
			bookCopy.setBranch(branch);
			bookCopy.setBook(b);
			
			if (libServe.getCurrBookCopies(bookCopy) == -1)
				bookCopy.setNoOfCopies(libServe.getCurrBookCopies(bookCopy) + 1);
			else
				bookCopy.setNoOfCopies(libServe.getCurrBookCopies(bookCopy));
			
			bookCopyList.add(bookCopy);
		}
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/Librarian/detailedBranch.jsp");

		request.setAttribute("CompleteCopies", bookCopyList);
		request.setAttribute("CompleteBranch", branch);	
		
		rd.forward(request, response);
	}
}