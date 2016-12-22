package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.Publisher;

public class BookDAO extends BaseDAO<List<Book>> {

	// store values generated on inserts
	private int bookId;
	private int pubId;

	private int authorId;
	private int genreID;

	public BookDAO(Connection conn) {
		super(conn);
	}

	public void create(Book book) throws Exception {

		// Insert book entry into book table
		bookId = saveWithId("insert into tbl_book (title) values (?)",
				new Object[] { book.getTitle() });

		AuthorDAO authorManager = new AuthorDAO(super.getConnection());
		// Retrieve list of authors for the book being inserted and add them to
		// table
		for (Author bkAuthors : book.getAuthors()) {

			// insert into author table each author associated with that book
			// get each author id that is generated
			authorId = authorManager.create(bkAuthors);

			save("insert into tbl_book_authors (bookId, authorId) values (?, ?)",
					new Object[] { bookId, authorId});//, pubId });

		}

		GenreDAO genreManager = new GenreDAO(super.getConnection());
		// Retrieve list of genres for the book being inserted and add them to
		// table
		for (Genre bkGenres : book.getGenres()) {
			
			Genre checkGenres = genreManager.readOne(bkGenres.getGenreName());
			
			// If genre exists in table then update genre/book table and 
			// do not insert new value into genre table
			if (checkGenres == null) {
				// insert into genre table each genre associated with that book
				// get each genre id that is generated
				genreID = genreManager.create(bkGenres);

				save("insert into tbl_book_genres (genre_id, bookId) values (?, ?)",
						new Object[] { genreID, bookId });
			} else {
				save("insert into tbl_book_genres (genre_id, bookId) values (?, ?)",
						new Object[] { checkGenres.getGenreId(), bookId });
			}

		}

		// insert publisher into publisher table then get pubId
		if (book.getPublisher() != null) {
			PublisherDAO pubManager = new PublisherDAO(super.getConnection());
			
			Publisher checkPub = pubManager.readOne(book.getPublisher().getPublisherName());
			
			if (checkPub == null) {
				pubId = pubManager.create(book.getPublisher());
	
				save("update tbl_book set pubId = ? where bookId = ?",
						new Object[] { pubId, bookId });
			}else{				
				save("update tbl_book set pubId = ? where bookId = ?",
						new Object[] { checkPub.getPublisherId(), bookId });				
			}
		}
	}

	public void update(Book book) throws Exception {
		save("update tbl_book set tbl_book.title = ? where tbl_book.bookId = ?",
				new Object[] { book.getTitle(), book.getBookId() });
	}

	public void delete(Book book) throws Exception {
		
		save("delete from tbl_book where tbl_book.bookId = ?",
				new Object[] { book.getBookId() });
	}

	@SuppressWarnings("unchecked")
	public List<Book> readAll() throws Exception {
		return (List<Book>) read("select * from tbl_book", null);
	}

	// Return record of book in table based on a given bookId
	@SuppressWarnings("unchecked")
	public Book readOne(int bookId) throws Exception {
		List<Book> booklist = (List<Book>) read("select * from tbl_book where bookId = ?", new Object[] { bookId });

		if (booklist != null && booklist.size() > 0) {
			return booklist.get(0);
		} else {
			return null;
		}
	}

	// Return record of book in table based on a given title
	@SuppressWarnings("unchecked")
	public Book readOne(String bookName) throws Exception {
		List<Book> booklist = (List<Book>) read(
				"select * from tbl_book where title = ?",
				new Object[] { bookName });

		if (booklist != null && booklist.size() > 0) {
			return booklist.get(0);
		} else {
			return null;
		}
	}

	// Read all from one branch by branch id
	@SuppressWarnings("unchecked")
	public List<Book> readAllInBranch(int branchId) throws Exception {
		List<Book> list = (List<Book>) read(
				"select * from tbl_book AS tbk "
						+ "INNER JOIN tbl_book_copies AS tbkc ON tbk.bookId = tbkc.bookId "
						+ "INNER JOIN tbl_library_branch AS tlb ON tbkc.branchId = tlb.branchId "
						+ "INNER JOIN tbl_book_authors AS tbkauth ON tbkauth.bookId = tbk.bookId "
						+ "INNER JOIN tbl_author AS tauth ON tbkauth.authorId = tauth.authorId "
						+ "WHERE tlb.branchId = ? and tbkc.noOfCopies >= 1 "
						+ "GROUP BY tbk.title", new Object[] { branchId });
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Book> readAllWithAuthor() throws Exception {
		List<Book> list = (List<Book>) read(
				"select * from tbl_book AS tbk "
						+ "INNER JOIN tbl_book_authors AS tbkauth ON tbkauth.bookId = tbk.bookId "
						+ "INNER JOIN tbl_author AS tauth ON tbkauth.authorId = tauth.authorId "
						+ "GROUP BY tbk.title", null);
		return list;
	}

	// Read all books loaned from one branch
	@SuppressWarnings("unchecked")
	public List<Book> readAllLoanedWithAuthor(int cardNo) throws Exception {
		List<Book> list = (List<Book>) read(
				"SELECT * from tbl_book AS tbk "
						+ "INNER JOIN tbl_book_authors AS tbkauth ON tbkauth.bookId = tbk.bookId "
						+ "INNER JOIN tbl_author AS tauth ON tbkauth.authorId = tauth.authorId "
						+ "INNER JOIN tbl_book_loans as tblbkln ON tblbkln.bookId = tbk.bookId "
						+ "INNER JOIN tbl_library_branch as tbranch ON tbranch.branchId = tblbkln.branchId "
						+ "WHERE tblbkln.cardNo = ? and tblbkln.dateIn IS NULL "
						+ "GROUP BY tbk.title", new Object[] { cardNo });
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Book> readAllWithAuthor(String bookName) throws Exception {
		List<Book> list = (List<Book>) read(
				"SELECT * from tbl_book AS tbk "
						+ "INNER JOIN tbl_book_authors AS tbkauth ON tbkauth.bookId = tbk.bookId "
						+ "INNER JOIN tbl_author AS tauth ON tbkauth.authorId = tauth.authorId "
						+ "INNER JOIN tbl_book_genres as tblbkgen ON tblbkgen.bookId = tbk.bookId "
						+ "INNER JOIN tbl_genre as tbgen ON tbgen.genre_id = tblbkgen.genre_id "
						+ "WHERE tbk.title = ? GROUP BY tauth.authorName", new Object[] { bookName });
		return list;	
	}
	
	@SuppressWarnings("unchecked")
	public List<Book> readAllWithGenre(String bookName) throws Exception {
		List<Book> list = (List<Book>) read(
				"SELECT * from tbl_book AS tbk "
						+ "INNER JOIN tbl_book_authors AS tbkauth ON tbkauth.bookId = tbk.bookId "
						+ "INNER JOIN tbl_author AS tauth ON tbkauth.authorId = tauth.authorId "
						+ "INNER JOIN tbl_book_genres as tblbkgen ON tblbkgen.bookId = tbk.bookId "
						+ "INNER JOIN tbl_genre as tbgen ON tbgen.genre_id = tblbkgen.genre_id "
						+ "WHERE tbk.title = ? GROUP BY tbgen.genre_name", new Object[] { bookName });
		return list;	
	}

	/*@Override
	public List<Book> extractData(ResultSet rs) throws SQLException {
		List<Book> booklist = new ArrayList<Book>();
		while (rs.next()) {

			Book book = new Book();
			book.setBookId(rs.getInt("bookId"));
			book.setTitle(rs.getString("title"));

			if (hasColumn(rs, "authorName")) {
				AuthorDAO authorManager = new AuthorDAO(super.getConnection());
				String authorName;
				Author authorObj = new Author();
				List<Author> authorsList = new ArrayList<Author>();

				authorName = (rs.getString("authorName"));
				authorObj = authorManager.readOne(authorName);				
				authorsList.add(authorObj);
				book.setAuthors(authorsList);
			}
			
			if (hasColumn(rs, "genre_name")) {
				GenreDAO genreManager = new GenreDAO(super.getConnection());
				
				String genreName;
				Genre genreObj = new Genre();
				List<Genre> genreList = new ArrayList<Genre>();

				genreName = (rs.getString("genre_name"));
				genreObj= genreManager.readOne(genreName);
				genreList.add(genreObj);
				book.setGenres(genreList);
			}

			booklist.add(book);
		}
		return booklist;
	}*/
	
	
	@Override
	public List<Book> extractData(ResultSet rs) {
		List<Book> booklist = new ArrayList<Book>();
		try {
			while (rs.next()) {

				Book book = new Book();
				book.setBookId(rs.getInt("bookId"));
				book.setTitle(rs.getString("title"));

				if (hasColumn(rs, "authorName")) {
					AuthorDAO authorManager = new AuthorDAO(super.getConnection());
					String authorName;
					Author authorObj = new Author();
					List<Author> authorsList = new ArrayList<Author>();

					authorName = (rs.getString("authorName"));
					authorObj = authorManager.readOne(authorName);				
					authorsList.add(authorObj);
					book.setAuthors(authorsList);
				}
				
				if (hasColumn(rs, "genre_name")) {
					GenreDAO genreManager = new GenreDAO(super.getConnection());
					
					String genreName;
					Genre genreObj = new Genre();
					List<Genre> genreList = new ArrayList<Genre>();

					genreName = (rs.getString("genre_name"));
					genreObj= genreManager.readOne(genreName);
					genreList.add(genreObj);
					book.setGenres(genreList);
				}

				booklist.add(book);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return booklist;
}
	

	// Check if specific column exists in table
	public static boolean hasColumn(ResultSet rs, String columnName)
			throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int columns = rsmd.getColumnCount();
		for (int x = 1; x <= columns; x++) {
			if (columnName.equals(rsmd.getColumnName(x))) {
				return true;
			}
		}
		return false;
	}
}