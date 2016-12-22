package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Author;

public class AuthorDAO extends BaseDAO<List<Author>> {

	public AuthorDAO(Connection conn) {
		super(conn);
	}

	public int create(Author author) throws Exception {
		return saveWithId("insert into tbl_author (authorName) values (?)",
				new Object[] { author.getAuthorName() });
	}

	public void insertInto(Author author, int bookId) throws Exception {
		save("insert into tbl_book_authors (bookId, authorId) values (?, ?)",
				new Object[] { bookId, author.getAuthorId() });
	}

	public void update(Author author) throws Exception {
		save("update tbl_author set authorName = ? where authorId = ?",
				new Object[] { author.getAuthorName(), author.getAuthorId() });
	}

	public void delete(Author author) throws Exception {
		save("delete from tbl_author where authorId = ?",
				new Object[] { author.getAuthorId() });
	}


	public List<Author> readAllAuthors(Integer pageNo, String searchString) throws SQLException{
		setPageNo(pageNo);
		if(searchString!=null && !searchString.isEmpty()){
			searchString = "%"+searchString+"%";
			return readAll("select * from tbl_author where authorName like ?", new Object[]{searchString});
		}else{
			return readAll("select * from tbl_author", null);
		}
	}


	@SuppressWarnings("unchecked")
	public List<Author> readAll() throws Exception {
		return (List<Author>) read("select * from tbl_author", null);
	}

	@SuppressWarnings("unchecked")
	public Author readOne(int authorId) throws Exception {
		List<Author> list = (List<Author>) read(
				"select * from tbl_author where authorId = ?",
				new Object[] { authorId });

		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public Author readOne(String authorName) throws Exception {
		List<Author> list = (List<Author>) read(
				"select * from tbl_author where authorName = ?",
				new Object[] { authorName });

		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}	

	@Override
	public List<Author> extractData(ResultSet rs) {
		List<Author> list = new ArrayList<Author>();
		try{
			while (rs.next()) {
				Author a = new Author();
				a.setAuthorId(rs.getInt("authorId"));
				a.setAuthorName(rs.getString("authorName"));

				list.add(a);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public Integer getAuthorCount(String searchString) throws SQLException {
		setPageNo(getPageNo());
		if(searchString!=null && !searchString.isEmpty()){
			searchString = "%"+searchString+"%";
			return getCount("select count(*) AS COUNT from tbl_author where authorName like ?", new Object[]{searchString});
		}else{
			return getCount("select count(*) AS COUNT from tbl_author", null);
		}
	}


}