package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Genre;

public class GenreDAO extends BaseDAO<List<Genre>> {

	public GenreDAO(Connection conn) {
		super(conn);
	}

	public int create(Genre genre) throws Exception {
		return saveWithId("insert into tbl_genre (genre_name) values (?)",
				new Object[] { genre.getGenreName() });
	}

	public void insertInto(Genre genre, int bookId) throws Exception {
		save("insert into tbl_book_genres (genre_id, bookId) values (?, ?)",
				new Object[] { genre.getGenreId(), bookId });
	}

	public void update(Genre genre) throws Exception {
		save("update tbl_genre set genre_name = ? where genre_id = ?",
				new Object[] { genre.getGenreName(), genre.getGenreId() });
	}

	public void delete(Genre genre, Book book) throws Exception {

		List<Genre> genreList = new ArrayList<Genre>();

		genreList = readAllAssociations(genre);

		if (genreList.size() > 1) {
			save("delete from tbl_book_genres where genre_id = ? and bookId = ?",
					new Object[] { genre.getGenreId(), book.getBookId() });
		} else {
			save("delete from tbl_genre where genre_id = ?",
					new Object[] { genre.getGenreId() });
		}
	}

	public void delete(Genre genre) throws Exception {

		save("delete from tbl_genre where genre_id = ?",
				new Object[] { genre.getGenreId() });
	}

	// Return list of all records in genre table
	@SuppressWarnings("unchecked")
	public List<Genre> readAll() throws Exception {
		return (List<Genre>) read("select * from tbl_genre", null);
	}

	// Return list of all records in genre table
	@SuppressWarnings("unchecked")
	public List<Genre> readAllAssociations(Genre genre) throws Exception {
		return (List<Genre>) read(
				"select * from tbl_book_genres where genre_id = ?",
				new Object[] { genre.getGenreId() });
	}

	// Return record of genre in table based on a given genreId
	@SuppressWarnings("unchecked")
	public Genre readOne(int genreId) throws Exception {
		List<Genre> list = (List<Genre>) read(
				"select * from tbl_genre where genre_id = ?",
				new Object[] { genreId });

		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	// Return record of genre in table based on a given genre name
	@SuppressWarnings("unchecked")
	public Genre readOne(String genreName) throws Exception {
		List<Genre> list = (List<Genre>) read(
				"select * from tbl_genre where genre_name = ?",
				new Object[] { genreName });

		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	protected List<Genre> extractData(ResultSet rs) throws SQLException {
		List<Genre> list = new ArrayList<Genre>();
		while (rs.next()) {
			Genre g = new Genre();

			if (hasColumn(rs, "genre_id") && hasColumn(rs, "bookId")) {

				g.setGenreId(rs.getInt("genre_id"));
				list.add(g);
			}

			else if (hasColumn(rs, "genre_name") && hasColumn(rs, "genre_id")) {
				g.setGenreId(rs.getInt("genre_id"));
				g.setGenreName(rs.getString("genre_name"));
				list.add(g);
			}

		}
		return list;
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