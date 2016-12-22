package com.gcit.lms.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.LibraryBranch;
import com.gcit.lms.entity.Publisher;
import com.gcit.lms.service.AdminService;
import com.gcit.lms.service.ConnectionUtil;

/**
 * Servlet implementation class AdministratorServlet
 */
@WebServlet({ "/addAuthor", "/editAuthor", "/deleteAuthor", "/addPublisher",
	"/deletePublisher", "/addBook", "/viewBook", "/deleteBook", "/detailedBook",
	"/updateBook", "/deleteBorrower", "/addBorrower", "/addBranch",
	"/deleteBranch", "/addGenre", "/deleteGenre", "/editGenre",
	"/editPublisher", "/editBranch", "/editBorrower", "/addAuthorBook", "/addGenreBook", 
	"/editAuthorBook", "/editGenreBook" , "/deleteBookAuthor", "/deleteBookGenre", "/searchAuthors", "/pageAuthors" })
public class AdministratorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	AdminService service = new AdminService();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdministratorServlet() {
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
		Boolean isAjax = false;
		switch (function) {

		case "/addAuthor": {
			addAuthor(request, response);
			break;
		}
		case "/addAuthorBook": {
			try {
				addBookAuthor(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		case "/addGenreBook": {
			try {
				addBookGenre(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		case "/addBook": {
			try {
				addBook(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		case "/addBranch": {
			addBranch(request, response);
			break;
		}
		case "/addBorrower": {
			addBorrower(request, response);
			break;
		}
		case "/addGenre": {
			addGenre(request, response);
			break;
		}
		case "/addPublisher": {
			addPublisher(request, response);
			break;
		}
		case "/editAuthor": {
			try {
				editAuthor(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		case "/editAuthorBook": {
			try {
				editAuthorBook(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}		
		case "/editGenre": {
			try {
				editGenre(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		case "/editGenreBook": {
			try {
				editGenreBook(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		case "/editPublisher": {
			try {
				editPublisher(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		case "/editBranch": {
			try {
				editBranch(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		case "/editBorrower": {
			try {
				editBorrower(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		case "/updateBook": {
			try {
				updateBook(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		case "/viewBook": {
			try {
				viewBook(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		case "/deleteAuthor": {
			deleteAuthor(request, response);
			break;
		}
		case "/deletePublisher": {
			deletePublisher(request, response);
			break;
		}
		case "/deleteBook": {
			try {
				deleteBook(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		case "/deleteBookAuthor": {
			try {
				deleteBookAuthor(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		case "/deleteBookGenre": {
			try {
				deleteBookGenre(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		case "/deleteBorrower": {
			deleteBorrower(request, response);
			break;
		}
		case "/deleteBranch": {
			deleteBranch(request, response);
			break;
		}
		case "/deleteGenre": {
			deleteGenre(request, response);
			break;
		}
		//default:
		//break;
		case "/pageAuthors"://///////////////////////////////////////////////////////
			
			Integer pageNo = Integer.parseInt(request.getParameter("pageNo"));
			String searchString = request.getParameter("searchString");
			System.out.println("pageNo"+pageNo+" serach:"+searchString);
			List<Author> authors = new ArrayList<Author>();
			try {
				authors = service.readAllAuthors(pageNo, searchString);

				RequestDispatcher rd = getServletContext().getRequestDispatcher(
						"/listAuthors.jsp");

				//forwardPage = "viewauthors.jsp";
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("authors", authors);
			request.setAttribute("searchAuthors", searchString);
			break;
		case "/searchAuthors":
			searchAuthorsAJAX(request, response);
			isAjax = true;
			break;
		default:
			break;
		}
		if (!isAjax) {
			//	RequestDispatcher rd = request.getRequestDispatcher(forwardPage);
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/listAuthors.jsp");
			rd.forward(request, response);
		}
	}

	//	}

	/* Genre Methods */


	private void searchAuthorsAJAX(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String searchString = request.getParameter("searchString");
		
		Integer count = 10;
		
		List<Author> authors = new ArrayList<Author>();
		try {
			authors = service.readAllAuthors(1, searchString);
			count = service.getAuthorCount(searchString);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		request.setAttribute("authors", authors);
		request.setAttribute("searchString", searchString);
		StringBuilder str = new StringBuilder();

		
		Integer pages = 1;
		
		if (count % 10 > 0) {
			pages = (count / 10) + 1;
		} else {
			pages = (count / 10);
		}

		str.append("<nav aria-label='Page navigation'>	<ul class='pagination'> <li><a href='#' aria-label='Previous'> <span aria-hidden='true'>&laquo;</span> </a></li>");

		for (int i = 1; i <= pages; i++) {
			
			str.append("<li><a href='pageAuthors?pageNo=" + i);
			
			if(searchString != null) {
				str.append("&searchString="+ searchString +"'>" + i + "</a></li>");
			} else {
					str.append("'>" + i + "></a></li>");
			}
		}
		
		str.append("<li><a href='#' aria-label='Next'> <span aria-hidden='true'>&raquo;</span></a></li></ul></nav>");

		str.append("<table class='table'><tr><th>#</th><th>Author Name</th><th>Edit Author</th><th>Delete Author</th></tr>");
		for (Author a : authors) {
			str.append("<tr><td>" + authors.indexOf(a) + "</td>");
			str.append("<td>" + a.getAuthorName() + "</td>");

			str.append("<td><button class='btn btn-info' data-toggle='modal' data-target='#editauthormodal' href='editauthor.jsp?authorId="
					+ a.getAuthorId() + "'>Edit</button></td>");

			str.append("<td><button class='btn btn-danger' data-toggle='modal'  href='javascript:deleteAuthor?authorId="
					+ a.getAuthorId() + "'>Delete</button></td>");
		}
		str.append("</table>");

		response.getWriter().append(str);
	}


	private void editGenre(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String genreName = request.getParameter("genreName");
		Integer genreId = Integer.parseInt(request.getParameter("genreId"));
		//		String genreId = request.getParameter("genreEditId");

		Genre genre = new Genre();
		genre.setGenreName(genreName);
		genre.setGenreId(Integer.parseInt(request.getParameter("genreId")));

		request.setAttribute("genre", genre);

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/editGenre.jsp");
		try{
			AdminService admin = new AdminService();
			admin.updateGenre(genre);
			request.setAttribute("result", "Genre update");
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("result", "failed: " + e.getMessage());
		}

		rd.forward(request, response);		
	}

	private void editGenreBook(HttpServletRequest request,
			HttpServletResponse response) throws Exception {	
		int bookId = Integer.parseInt(request.getParameter("bookEditId"));
		String genreName = request.getParameter("genreEditName");
		String genreId = request.getParameter("genreEditId");

		Book book = new Book();

		Genre genre = new Genre();
		genre.setGenreName(genreName);
		genre.setGenreId(Integer.parseInt(genreId));

		AdminService admin = new AdminService();
		admin.updateGenre(genre);

		book.setBookId(bookId);
		book = admin.getBookById(book.getBookId());
		book = admin.completeBook(book);

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/detailedBook.jsp");

		request.setAttribute("CompleteBook", book);

		rd.forward(request, response);
	}

	private void addGenre(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// get data from webpage
		String genreName = request.getParameter("genreName");
		Genre genre = new Genre();
		genre.setGenreName(genreName);
		;

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/genre.jsp");
		try {
			// Call method to add
			new AdminService().addGenre(genre);

			request.setAttribute("result", "Genre Added Succesfully!");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result",
					"Genre Add Failed because: " + e.getMessage());
		}

		rd.forward(request, response);
	}

	private void deleteGenre(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String genreId = request.getParameter("genreId");
		Genre genre = new Genre();
		genre.setGenreId(Integer.parseInt(genreId));

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/listGenre.jsp");
		try {
			AdminService admin = new AdminService();
			admin.deleteGenre(genre);

			request.setAttribute("result", "Genre Deleted Succesfully!");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result",
					"Genre Delete Failed because: " + e.getMessage());
		}

		rd.forward(request, response);
	}

	private void deleteBookGenre(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int bookId = Integer.parseInt(request.getParameter("bookEditId"));
		String genreId = request.getParameter("genreId");

		Book book = new Book();
		Genre genre = new Genre();
		genre.setGenreId(Integer.parseInt(genreId));

		AdminService admin = new AdminService();

		book.setBookId(bookId);
		book = admin.getBookById(book.getBookId());
		book = admin.completeBook(book);

		admin.deleteGenreBook(genre, book);

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/detailedBook.jsp");

		book = admin.completeBook(book);

		request.setAttribute("CompleteBook", book);

		rd.forward(request, response);
	}

	/* Branch Methods */

	private void addBranch(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Get data from webpage
		String branchName = request.getParameter("branchName");
		String branchAddress = request.getParameter("branchAddress");

		// Set data into publisher object
		LibraryBranch branch = new LibraryBranch();
		branch.setBranchName(branchName);
		branch.setBranchAddress(branchAddress);

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/branch.jsp");
		try {
			// Call method in admin service to add publisher tto database
			new AdminService().addBranch(branch);

			request.setAttribute("result", "Branch Added Succesfully!");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result",
					"Branch Add Failed because: " + e.getMessage());
		}

		rd.forward(request, response);
	}

	private void deleteBranch(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String branchId = request.getParameter("branchId");

		LibraryBranch branch = new LibraryBranch();
		branch.setBranchId(Integer.parseInt(branchId));

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/listBranch.jsp");
		try {
			AdminService admin = new AdminService();
			admin.deleteBranch(branch);

			request.setAttribute("result", "Branch Deleted Succesfully!");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result",
					"Branch Delete Failed because: " + e.getMessage());
		}

		rd.forward(request, response);
	}

	private void editBranch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String branchName = request.getParameter("branchName");
		String branchAddress = request.getParameter("branchAddress");
		Integer branchId = Integer.parseInt(request.getParameter("branchId"));
		//		String branchId = request.getParameter("branchEditId");
		LibraryBranch branch = new LibraryBranch();
		branch.setBranchName(branchName);
		branch.setBranchAddress(branchAddress);
		branch.setBranchId(Integer.parseInt(request.getParameter("branchId")));

		request.setAttribute("branch", branch);
		//		System.out.println(branch.getBranchName());
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/editBranch.jsp");
		try{
			AdminService admin = new AdminService();
			admin.updateBranch(branch);
			request.setAttribute("result", "Branch updated");
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("result", "failed: " + e.getMessage());
		}
		rd.forward(request, response);
	}

	/* Borrower Methods */


	private void editBorrower(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String borrowerName = request.getParameter("borrowerName");
		String borrowerPhone = request.getParameter("borrowerPhone");
		String borrowerAddress = request.getParameter("borrowerAddress");

		Integer cardNo = Integer.parseInt(request.getParameter("cardNo"));
		//		String cardNo = request.getParameter("cardEditNo");
		Borrower borrower = new Borrower();
		borrower.setBorrowerName(borrowerName);
		borrower.setBorrowerPhone(borrowerPhone);
		borrower.setBorrowerAddress(borrowerAddress);
		borrower.setCardNo(Integer.parseInt(request.getParameter("cardNo")));

		request.setAttribute("borrower", borrower);
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/editBorrower.jsp");

		try{
			AdminService admin = new AdminService();
			admin.updateBorrower(borrower);
			request.setAttribute("result", "Borrower updated");
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("result", "failed: " + e.getMessage());
		}
		rd.forward(request, response);
	}

	private void addBorrower(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Get data from webpage
		String borrowerName = request.getParameter("borrowerName");
		String borrowerAddress = request.getParameter("borrowerAddress");
		String borrowerPhone = request.getParameter("borrowerPhone");

		// Set data into publisher object
		Borrower borrower = new Borrower();
		borrower.setBorrowerName(borrowerName);
		borrower.setBorrowerAddress(borrowerAddress);
		borrower.setBorrowerPhone(borrowerPhone);

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/borrower.jsp");
		try {
			// Call method in admin service to add publisher tto database
			new AdminService().addBorrower(borrower);

			request.setAttribute("result", "Borrower Added Succesfully!");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result",
					"Borrower Add Failed because: " + e.getMessage());
		}

		rd.forward(request, response);

	}

	private void deleteBorrower(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String cardNo = request.getParameter("cardNo");
		Borrower borrower = new Borrower();
		borrower.setCardNo(Integer.parseInt(cardNo));

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/listBorrower.jsp");
		try {
			AdminService admin = new AdminService();
			admin.deleteBorrower(borrower);

			request.setAttribute("result", "Borrower Deleted Succesfully!");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", "Borrower Delete Failed because: "
					+ e.getMessage());
		}

		rd.forward(request, response);
	}

	/* Book Methods */

	private void addBookAuthor(HttpServletRequest request,
			HttpServletResponse response)throws Exception {

		int bookId = Integer.parseInt(request.getParameter("bookEditId"));
		String authorName = request.getParameter("authorAddName");
		AdminService admin = new AdminService();

		Book book = new Book();

		Author author = new Author();
		author.setAuthorName(authorName);

		admin.addAuthorToBook(author, bookId);

		book.setBookId(bookId);
		book = admin.getBookById(book.getBookId());
		book = admin.completeBook(book);

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/detailedBook.jsp");

		request.setAttribute("CompleteBook", book);

		rd.forward(request, response);
	}

	private void addBookGenre(HttpServletRequest request,
			HttpServletResponse response)throws Exception {

		int bookId = Integer.parseInt(request.getParameter("bookEditId"));
		String genreName = request.getParameter("genreAddName");
		AdminService admin = new AdminService();

		Book book = new Book();

		Genre genre = new Genre();
		genre.setGenreName(genreName);

		admin.addGenreToBook(genre, bookId);

		book.setBookId(bookId);
		book = admin.getBookById(book.getBookId());
		book = admin.completeBook(book);

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/detailedBook.jsp");

		request.setAttribute("CompleteBook", book);

		rd.forward(request, response);
	}

	private void updateBook(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String bookId = request.getParameter("bookEditId");
		String newName = request.getParameter("bookTitle");
		Book book = new Book();
		AdminService admin = new AdminService();

		book.setBookId(Integer.parseInt(bookId));
		book.setTitle(newName);

		admin.updateBook(book);

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/listBooks.jsp");

		rd.forward(request, response);
	}

	private void deleteBook(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String bookId = request.getParameter("bookDeleteId");
		Book book = new Book();
		AdminService admin = new AdminService();

		book.setBookId(Integer.parseInt(bookId));
		book = admin.getBookById(book.getBookId());
		book = admin.completeBook(book);

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/listBooks.jsp");

		try {
			new AdminService().deleteBook(book);
			request.setAttribute("result", "Book Deleted Succesfully!");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result",
					"Book Delete Failed because: " + e.getMessage());
		}

		rd.forward(request, response);
	}

	private void addBook(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// get data from webpage
		String[] authorIds = request.getParameterValues("authorId");
		String[] genreIds = request.getParameterValues("genreId");
		//	String publisherName = request.getParameter("publisherName");
		//		Integer publisherId = Integer.parseInt(request.getParameter("publisherId"));
		String title = request.getParameter("title");

		//	System.out.println(publisherId);

		Book bk = new Book();
		bk.setTitle(title);
		AdminService admin = new AdminService();

		if (authorIds != null && authorIds.length > 0) {
			bk.setAuthors(new ArrayList<Author>());
			for (String s : authorIds) {
				Author author = new Author();
				author.setAuthorId(Integer.parseInt(s));
				author = admin.getAuthorById(author.getAuthorId());
				bk.getAuthors().add(author);
			}
		}

		if (genreIds != null && genreIds.length > 0) {
			bk.setGenres(new ArrayList<Genre>());
			for (String s : genreIds) {
				Genre genre = new Genre();
				genre.setGenreId(Integer.parseInt(s));
				genre = admin.getGenreById(genre.getGenreId());
				bk.getGenres().add(genre);
			}
		}
		/*Publisher publisher = new Publisher();
		publisher.setPublisherName(publisherName);
		publisher.setPublisherId(Integer.parseInt(request.getParameter("publisherId")));*/


		/*	if (publisherIds != null && publisherIds.length > 0) {
			bk.setPublishers(new ArrayList<Publisher>());
			for (String s : publisherIds) {
				Publisher publisher = new Publisher();
				publisher.setPublisherId(Integer.parseInt(s));
				publisher = admin.getPublisherById(publisher.getPublisherId());
				bk.getPublishers().add(publisher);
			}
		}*/

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/books.jsp");
		try {
			new AdminService().addBookDetails(bk);

			request.setAttribute("result", "Book Added Succesfully!");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result",
					"Book Add Failed because: " + e.getMessage());
		}

		rd.forward(request, response);
	}

	private void viewBook(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String bookId = request.getParameter("bookViewId");
		Book book = new Book();
		AdminService admin = new AdminService();

		book.setBookId(Integer.parseInt(bookId));
		book = admin.getBookById(book.getBookId());
		book = admin.completeBook(book);

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/detailedBook.jsp");

		request.setAttribute("CompleteBook", book);

		rd.forward(request, response);
	}

	/* Publisher Methods */

	private void deletePublisher(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String pubId = request.getParameter("pubId");
		Publisher publisher = new Publisher();
		publisher.setPublisherId(Integer.parseInt(pubId));

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/listPublisher.jsp");
		try {
			new AdminService().deletePublisher(publisher);

			request.setAttribute("result", "Publisher Deleted Succesfully!");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", "Publisher Delete Failed because: "
					+ e.getMessage());
		}

		rd.forward(request, response);
	}

	private void addPublisher(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// Get data from webpage
		String pubName = request.getParameter("pubName");
		String pubAddress = request.getParameter("pubAddress");
		String pubPhone = request.getParameter("pubPhone");

		// Set data into publisher object
		Publisher publisher = new Publisher();
		publisher.setPublisherName(pubName);
		publisher.setPublisherAddress(pubAddress);
		publisher.setPublisherPhone(pubPhone);

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/publisher.jsp");
		try {
			// Call method in admin service to add publisher tto database
			new AdminService().addPublisher(publisher);

			request.setAttribute("result", "Publisher Added Succesfully!");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result",
					"Publisher Add Failed because: " + e.getMessage());
		}

		rd.forward(request, response);
	}

	private void editPublisher(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String publisherName = request.getParameter("publisherName");
		String publisherAddress = request.getParameter("publisherAddress");
		String publisherPhone = request.getParameter("publisherPhone");
		Integer publisherId = Integer.parseInt(request.getParameter("publisherId"));
		//String pubId = request.getParameter("pubEditId");


		Publisher publisher = new Publisher();
		publisher.setPublisherName(publisherName);
		publisher.setPublisherAddress(publisherAddress);
		publisher.setPublisherPhone(publisherPhone);
		publisher.setPublisherId(Integer.parseInt(request.getParameter("publisherId")));

		request.setAttribute("publisher", publisher);

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/editPublisher.jsp");
		try{
			AdminService admin = new AdminService();
			admin.updatePublisher(publisher);
			request.setAttribute("result", "Publisher updated");
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("result", "failed: " + e.getMessage());
		}
		rd.forward(request, response);
	}

	/* Author Methods */

	private void deleteAuthor(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String authorId = request.getParameter("authorId");
		Author author = new Author();
		author.setAuthorId(Integer.parseInt(authorId));

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/listAuthors.jsp");
		try {
			AdminService admin = new AdminService();
			admin.deleteAuthor(author);

			request.setAttribute("result", "Author Deleted Succesfully!");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result",
					"Author Delete Failed because: " + e.getMessage());
		}

		rd.forward(request, response);
	}

	private void addAuthor(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		// get data from webpage
		String authorName = request.getParameter("authorName");
		Author author = new Author();
		author.setAuthorName(authorName);

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/authors.jsp");
		try {
			// Call method to add
			new AdminService().addAuthor(author);

			request.setAttribute("result", "Author Added Succesfully!");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result",
					"Author Add Failed because: " + e.getMessage());
		}

		rd.forward(request, response);
	}


	private void editAuthor(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String authorName = request.getParameter("authorName");
		Integer authorId = Integer.parseInt(request.getParameter("authorId"));
		Author author = new Author();
		author.setAuthorName(authorName);
		author.setAuthorId(Integer.parseInt(request.getParameter("authorId")));

		System.out.println(author.getAuthorName());

		request.setAttribute("author", author);

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/editAuthor.jsp");

		try{
			AdminService admin = new AdminService();
			admin.updateAuthor(author);
			request.setAttribute("result", "Author updated");
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("result", "failed: " + e.getMessage());
		}

		//		AdminService admin = new AdminService();
		//		admin.updateAuthor(author);

		rd.forward(request, response);
	}


	private void editAuthorBook(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int bookId = Integer.parseInt(request.getParameter("bookEditId"));
		String authorName = request.getParameter("authorName");
		String authorId = request.getParameter("authorEditId");

		Book book = new Book();
		Author author = new Author();


		author.setAuthorName(authorName);
		author.setAuthorId(Integer.parseInt(authorId));

		AdminService admin = new AdminService();
		admin.updateAuthor(author);

		book.setBookId(bookId);
		book = admin.getBookById(book.getBookId());
		book = admin.completeBook(book);

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/detailedBook.jsp");

		request.setAttribute("CompleteBook", book);

		rd.forward(request, response);
	}

	// TODO: Error Check
	private void deleteBookAuthor(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int bookId = Integer.parseInt(request.getParameter("bookEditId"));
		String authorId = request.getParameter("authorId");

		Author author = new Author();
		author.setAuthorId(Integer.parseInt(authorId));

		Book book = new Book();

		AdminService admin = new AdminService();
		admin.deleteAuthor(author);

		book.setBookId(bookId);
		book = admin.getBookById(book.getBookId());
		book = admin.completeBook(book);

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/detailedBook.jsp");

		request.setAttribute("CompleteBook", book);

		rd.forward(request, response);
	}

	/*	public Integer getAuthorCount() throws SQLException, ClassNotFoundException{
		Connection conn =  ConnectionUtil.getConnection();
		try {
			AuthorDAO adao = new AuthorDAO(conn);
			return adao.getAuthorCount();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			conn.close();
		}
		return null;
}*/

}