package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookLoanDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookLoan;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.LibraryBranch;
import com.gcit.lms.entity.Publisher;

public class AdminService {

	/* Author Functions */

	public void addAuthor(Author author) throws Exception {
		Connection c = ConnectionUtil.getConnection();
		try {
			if (author == null || author.getAuthorName() == null
					|| author.getAuthorName().length() == 0
					|| author.getAuthorName().length() > 45) {
				throw new Exception(
						"Author cannot be null and Name should be 1-45 characters");
			}
			AuthorDAO aDAO = new AuthorDAO(c);
			aDAO.create(author);
			c.commit();
		} catch (Exception e) {
			c.rollback();
			throw e;
		} finally {
			c.close();
		}
	}
	
	public void addAuthorToBook(Author author, int bookId) throws Exception {
		Connection c = ConnectionUtil.getConnection();
		try {
			if (author == null || author.getAuthorName() == null
					|| author.getAuthorName().length() == 0
					|| author.getAuthorName().length() > 45) {
				throw new Exception(
						"Author cannot be null and Name should be 1-45 characters");
			}
			AuthorDAO aDAO = new AuthorDAO(c);
			author.setAuthorId(aDAO.create(author));
			aDAO.insertInto(author, bookId);
			c.commit();
		} catch (Exception e) {
			c.rollback();
			throw e;
		} finally {
			c.close();
		}
	}
	
	public void deleteAuthor(Author author) throws Exception {

		Connection c = ConnectionUtil.getConnection();
		AuthorDAO authorManager = new AuthorDAO(c);

		try {
			authorManager.delete(author);
			c.commit();
		} catch (Exception e) {
			c.rollback();
			throw e;
		} finally {
			c.close();
		}
		
	}
	
	public Integer getAuthorCount(String searchString) throws Exception{
		Connection c = ConnectionUtil.getConnection();
		AuthorDAO authorManager = new AuthorDAO(c);

		try {
			return authorManager.getAuthorCount(searchString);
		} catch (Exception e) {
			throw e;
		}
	}

	public void updateAuthor(Author author) throws Exception {
		Connection c = ConnectionUtil.getConnection();
		AuthorDAO authorManager = new AuthorDAO(c);

		try {
			authorManager.update(author);
			c.commit();
		} catch (Exception e) {
			c.rollback();
			throw e;
		} finally {
			c.close();
		}
	}

	public Author getAuthorByName(String authorName) throws Exception {
		Connection c = ConnectionUtil.getConnection();
		try {
			AuthorDAO aDAO = new AuthorDAO(c);
			return aDAO.readOne(authorName);
		} finally {
			c.close();
		}
	}
	
	public Author getAuthorById(int authorId) throws Exception {
		Connection c = ConnectionUtil.getConnection();
		try {
			AuthorDAO aDAO = new AuthorDAO(c);
			return aDAO.readOne(authorId);
		} finally {
			c.close();
		}
	}
	
	
	public List<Author> readAllAuthors(Integer pageNo, String searchString) throws SQLException{
		Connection conn = null;
		ConnectionUtil conUtil = new ConnectionUtil();
		try {
			conn = conUtil.getConnection();
			AuthorDAO adao = new AuthorDAO(conn);
			return adao.readAllAuthors(pageNo, searchString);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			conn.close();
		}
		return null;
	}
	
	
	////////////////////
	public LibraryBranch getBranchById(int branchId) throws Exception {
		Connection c = ConnectionUtil.getConnection();
		try {
			LibraryBranchDAO bDAO = new LibraryBranchDAO(c);
			return bDAO.readOne(branchId);
		} finally {
			c.close();
		}
	}
	
	public Borrower getBorrowerById(int borrowerId) throws Exception {
		Connection c = ConnectionUtil.getConnection();
		try {
			BorrowerDAO bDAO = new BorrowerDAO(c);
			return bDAO.readOne(borrowerId);
		} finally {
			c.close();
		}
	}
	
	public Publisher getPublisherById(int publisherId) throws Exception {
		Connection c = ConnectionUtil.getConnection();
		try {
			PublisherDAO pDAO = new PublisherDAO(c);
			return pDAO.readOne(publisherId);
		} finally {
			c.close();
		}
	}
	
	
	///////////////////

	public List<Author> getAllAuthors() throws Exception {

		Connection c = ConnectionUtil.getConnection();
		AuthorDAO authorManager = new AuthorDAO(c);

		List<Author> authorList = (List<Author>) authorManager.readAll();
		c.close();
		
		return authorList;
	}

	/* Book Functions */

	public Book getBookByName(String bookName) throws Exception {
		Connection c = ConnectionUtil.getConnection();
		try {
			BookDAO bDAO = new BookDAO(c);
			return bDAO.readOne(bookName);
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

	public void addBookDetails(Book book) throws Exception, SQLException {
		Connection c = ConnectionUtil.getConnection();
		try {
			BookDAO bookManager = new BookDAO(c);

			bookManager.create(book);

			c.commit();
		} catch (Exception e) {
			c.rollback();
			throw e;
		} finally {
			c.close();
		}

	}

	public List<Book> getAllBooks() throws Exception {

		Connection c = ConnectionUtil.getConnection();
		BookDAO bookManager = new BookDAO(c);

		List<Book> bookList = (List<Book>) bookManager.readAll();

		c.close();
		
		return bookList;
	}

	public Book completeBook(Book book) throws Exception {
		Connection c = ConnectionUtil.getConnection();
		BookDAO bookManager = new BookDAO(c);

		try {
			List<Book> authorbookList = (List<Book>) bookManager
					.readAllWithAuthor(book.getTitle());
			List<Author> authors = new ArrayList<Author>();

			for (Book b : authorbookList) {
				authors.add(b.getAuthors().get(0));
			}

			book.setAuthors(authors);

			List<Book> genrebookList = (List<Book>) bookManager
					.readAllWithGenre(book.getTitle());
			List<Genre> genres = new ArrayList<Genre>();

			for (Book b : genrebookList) {
				genres.add(b.getGenres().get(0));
			}

			book.setGenres(genres);

			return book;

		} finally {
			c.close();
		}
	}

	public void updateBook(Book book) throws Exception {
		Connection c = ConnectionUtil.getConnection();
		BookDAO bookManager = new BookDAO(c);
		/*AuthorDAO authorManager = new AuthorDAO(c);
		GenreDAO genreManager = new GenreDAO(c);*/
		try {
			bookManager.update(book);

			/*for (Genre g : book.getGenres()) {
				genreManager.update(g);
			}
			for (Author a : book.getAuthors()) {
				authorManager.update(a);
			}
			*/
			c.commit();
			
			System.out.println("Update Successful");
			
		} catch (Exception e) {
			c.rollback();
			throw e;
		} finally {
			c.close();
		}

	}
	
	public void deleteBook(Book book) throws Exception {
		
		Connection c = ConnectionUtil.getConnection();
		BookDAO bookManager = new BookDAO(c);
		AuthorDAO authorManager = new AuthorDAO(c);
		GenreDAO genreManager = new GenreDAO(c);
		
		try {
			bookManager.delete(book);
			
			for (Genre g : book.getGenres()) {
				genreManager.delete(g, book);
			}

			for (Author a : book.getAuthors()) {
				authorManager.delete(a);
			}
			
			c.commit();
			
			System.out.println("Delete Successful");
			
		} catch (Exception e) {
			c.rollback();
			throw e;
		} finally {
			c.close();
		}
		
	}

	/* Publisher Functions */

	public void addPublisher(Publisher publisher) throws Exception {
		Connection c = ConnectionUtil.getConnection();
		PublisherDAO pubManager = new PublisherDAO(c);

		try {
			pubManager.create(publisher);
			c.commit();
			System.out.println("Publisher Added Successfully");
			System.out.println(publisher.getPublisherName() + ", "
					+ publisher.getPublisherAddress() + ", "
					+ publisher.getPublisherPhone());
		} catch (Exception e) {
			c.rollback();
			throw e;
		} finally {
			c.close();
		}
	}

	public void updatePublisher(Publisher publisher) throws Exception {
		Connection c = ConnectionUtil.getConnection();
		PublisherDAO pubManager = new PublisherDAO(c);

		try {
			pubManager.update(publisher);
			c.commit();
			
			System.out.println();
			System.out.println("Update successful");
			System.out.println(publisher.getPublisherName() + ", "
					+ publisher.getPublisherAddress() + ", "
					+ publisher.getPublisherPhone());

		} catch (Exception e) {
			c.rollback();
			throw e;
		} finally {
			c.close();
		}
	}

	public List<Publisher> getAllPublishers() throws Exception {

		Connection c = ConnectionUtil.getConnection();
		PublisherDAO pubManager = new PublisherDAO(c);

		List<Publisher> pubList = (List<Publisher>) pubManager.readAll();

		c.close();
		
		return pubList;
	}

	public Publisher getPublisherByName(String pubName) throws Exception {

		Connection c = ConnectionUtil.getConnection();
		PublisherDAO pubManager = new PublisherDAO(c);
		
		Publisher p = pubManager.readOne(pubName);	
		c.close();
		
		return p;
	}

	public void deletePublisher(Publisher publisher) throws Exception {

		Connection c = ConnectionUtil.getConnection();
		PublisherDAO pubManager = new PublisherDAO(c);

		try {
			pubManager.delete(publisher);
			c.commit();
		} catch (Exception e) {
			c.rollback();
			throw e;
		} finally {
			c.close();
		}
	}

	/* Borrower Functions */

	public void addBorrower(Borrower borrower) throws Exception {
		Connection c = ConnectionUtil.getConnection();
		BorrowerDAO borrowerManager = new BorrowerDAO(c);

		try {
			borrowerManager.create(borrower);
			c.commit();
		} catch (Exception e) {
			c.rollback();
			throw e;
		} finally {
			c.close();
		}
	}

	public List<Borrower> getAllBorrowers() throws Exception {

		Connection c = ConnectionUtil.getConnection();
		BorrowerDAO borrowerManager = new BorrowerDAO(c);

		List<Borrower> borrowerList = (List<Borrower>) borrowerManager
				.readAll();
		
		c.close();
		
		return borrowerList;
	}

	public Borrower getBorrowerByName(String borrowerName) throws Exception {
		Connection c = ConnectionUtil.getConnection();
		BorrowerDAO borrowerManager = new BorrowerDAO(c);

		try {
			return borrowerManager.readOne(borrowerName);
		} finally {
			c.close();
		}
	}

	public void updateBorrower(Borrower borrower) throws Exception {

		Connection c = ConnectionUtil.getConnection();
		BorrowerDAO borrowerManager = new BorrowerDAO(c);

		try {
			borrowerManager.update(borrower);
			borrower = borrowerManager.readOne(borrower.getBorrowerName());

			c.commit();
			
			System.out.println();
			System.out.println("Update successful");
			System.out.println(borrower.getBorrowerName() + ", "
					+ borrower.getBorrowerAddress() + ", "
					+ borrower.getBorrowerPhone());

		} catch (Exception e) {
			c.rollback();
		} finally {
			c.close();
		}
	}

	public void deleteBorrower(Borrower borrower) throws Exception {

		Connection c = ConnectionUtil.getConnection();
		BorrowerDAO borrowerManager = new BorrowerDAO(c);

		try {
			borrowerManager.delete(borrower);
			c.commit();
		} catch (Exception e) {
			c.rollback();
			throw e;
		} finally {
			c.close();
		}
	}

	/* Branch Functions */

	public void addBranch(LibraryBranch branch) throws Exception {
		Connection c = ConnectionUtil.getConnection();
		LibraryBranchDAO branchManager = new LibraryBranchDAO(c);

		try {
			branchManager.create(branch);
			c.commit();
		} catch (Exception e) {
			c.rollback();
			throw e;
		} finally {
			c.close();
		}
	}

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

	public void updateBranch(LibraryBranch branch) throws ClassNotFoundException,
			SQLException {

		Connection c = ConnectionUtil.getConnection();
		LibraryBranchDAO branchManager = new LibraryBranchDAO(c);

		try {
			branchManager.update(branch);
			branch = branchManager.readOne(branch.getBranchName());

			c.commit();
			
			System.out.println();
			System.out.println("Update successful");
			System.out.println(branch.getBranchName() + ", "
					+ branch.getBranchAddress());

		} catch (Exception e) {
			c.rollback();
		} finally {
			c.close();
		}
	}

	public void deleteBranch(LibraryBranch branch) throws Exception {

		Connection c = ConnectionUtil.getConnection();
		LibraryBranchDAO branchManager = new LibraryBranchDAO(c);

		try {
			branchManager.delete(branch);
			c.commit();
		} catch (Exception e) {
			c.rollback();
			throw e;
		} finally {
			c.close();
		}
	}

	/* Over-ride Due Date */
	public void getBooksLoaned(BookLoan bookLoaned) throws Exception {

		Connection c = ConnectionUtil.getConnection();
		BookLoanDAO loansManager = new BookLoanDAO(c);

		List<BookLoan> loanList = new ArrayList<BookLoan>();

		System.out.println();
		System.out.println("Borrower: "
				+ bookLoaned.getBorrower().getBorrowerName() + ", "
				+ bookLoaned.getBorrower().getBorrowerAddress());

		System.out.println();
		System.out.println("Books Loaned: ");
		loanList = loansManager.readAll(bookLoaned);
		for (BookLoan bk : loanList) {
			System.out.println(bk.getBook().getTitle() + " loaned from "
					+ bk.getBranch().getBranchName());
		}
        c.close();
	}

	public void overRideDueDate(BookLoan bookLoaned) throws Exception {
		Connection c = ConnectionUtil.getConnection();
		BookLoanDAO loansManager = new BookLoanDAO(c);

		String dueDate = bookLoaned.getDueDate();
		bookLoaned = loansManager.readOneByBranch(bookLoaned);

		bookLoaned.setDueDate(dueDate);
		try {
			loansManager.updateDueDate(bookLoaned);
			c.commit();

			System.out.println();
			System.out.println("Update Successful");

		} catch (Exception e) {
			c.rollback();
			throw e;
		} finally {
			c.close();
		}
	}
	
	/*Genre Functions*/
	
	public void updateGenre(Genre genre) throws Exception {
		Connection c = ConnectionUtil.getConnection();
		GenreDAO genreManager = new GenreDAO(c);

		try {
			genreManager.update(genre);
			c.commit();
		} catch (Exception e) {
			c.rollback();
			throw e;
		} finally {
			c.close();
		}
	}

	public Genre getGenreByName(String genreName) throws Exception {
		Connection c = ConnectionUtil.getConnection();
		try {
			GenreDAO genreManager = new GenreDAO(c);
			return genreManager.readOne(genreName);
		} finally {
			c.close();
		}
	}
	
	public Genre getGenreById(int genreId) throws Exception {
		Connection c = ConnectionUtil.getConnection();
		try {
			GenreDAO genreManager = new GenreDAO(c);
			return genreManager.readOne(genreId);
		} finally {
			c.close();
		}
	}

	public List<Genre> getAllGenres() throws Exception {

		Connection c = ConnectionUtil.getConnection();
		GenreDAO genreManager = new GenreDAO(c);

		List<Genre> genreList = (List<Genre>) genreManager.readAll();
		c.close();
		
		return genreList;
	}

	public void addGenre(Genre genre) throws Exception {
		Connection c = ConnectionUtil.getConnection();
		GenreDAO genreManager = new GenreDAO(c);

		try {
			genreManager.create(genre);
			c.commit();
		} catch (Exception e) {
			c.rollback();
			throw e;
		} finally {
			c.close();
		}
	}

	public void deleteGenre(Genre genre) throws Exception{
		Connection c = ConnectionUtil.getConnection();
		GenreDAO genreManager = new GenreDAO(c);

		try {
			genreManager.delete(genre);
			c.commit();
		} catch (Exception e) {
			c.rollback();
			throw e;
		} finally {
			c.close();
		}		
	}
	
	public void deleteGenreBook(Genre genre, Book book) throws Exception{
		Connection c = ConnectionUtil.getConnection();
		GenreDAO genreManager = new GenreDAO(c);

		try {
			genreManager.delete(genre, book);
			c.commit();
		} catch (Exception e) {
			c.rollback();
			throw e;
		} finally {
			c.close();
		}		
	}

	public void addGenreToBook(Genre genre, int bookId) throws Exception {
		Connection c = ConnectionUtil.getConnection();
		try {
			GenreDAO gDAO = new GenreDAO(c);
			Genre checkGenres = gDAO.readOne(genre.getGenreName());
			
			// If genre exists in table then update genre/book table and 
			// do not insert new value into genre table
			if (checkGenres == null) {
				genre.setGenreId(gDAO.create(genre));
				gDAO.insertInto(genre, bookId);	
			}
			else {
				gDAO.insertInto(checkGenres, bookId);
			}

			c.commit();
		} catch (Exception e) {
			c.rollback();
			throw e;
		} finally {
			c.close();
		}
	}
}