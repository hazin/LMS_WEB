package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Publisher;

public class PublisherDAO extends BaseDAO<List<Publisher>> {

	public PublisherDAO(Connection conn) {
		super(conn);
	}

	public int create(Publisher publisher) throws Exception {

		return saveWithId(
				"insert into tbl_publisher (publisherName, publisherAddress, publisherPhone) "
						+ "values (?, ?, ?)",
				new Object[] { publisher.getPublisherName(),
						publisher.getPublisherAddress(), publisher.getPublisherPhone() });
	}

	public void update(Publisher publisher) throws Exception {
		save("update tbl_publisher set publisherName = ?, publisherAddress = ?, publisherPhone = ? where publisherId = ?",
				new Object[] { publisher.getPublisherName(),
						publisher.getPublisherAddress(), publisher.getPublisherPhone(),
						publisher.getPublisherId() });
	}

	public void delete(Publisher publisher) throws Exception {

		/*
		 * Remove publisher information from book table before deleting entry
		 * from publisher table
		 */
		save("UPDATE tbl_book SET tbl_book.pubId = NULL WHERE tbl_book.pubId = ?",
				new Object[] { publisher.getPublisherId() });

		// Delete publisher information
		save("delete from tbl_publisher where publisherId = ?",
				new Object[] { publisher.getPublisherId() });
	}

	@SuppressWarnings("unchecked")
	public List<Publisher> readAll() throws Exception {
		return (List<Publisher>) read("select * from tbl_publisher", null);
	}

	// Return record of publisher in table based on a given publisherId
	@SuppressWarnings("unchecked")
	public Publisher readOne(int publisherId) throws Exception {
		List<Publisher> pubList = (List<Publisher>) read(
				"select * from tbl_publisher where publisherId = ?",
				new Object[] { publisherId });

		if (pubList != null && pubList.size() > 0) {
			return pubList.get(0);
		} else {
			return null;
		}
	}

	// Return record of publisher in table based on a given publisherName
	@SuppressWarnings("unchecked")
	public Publisher readOne(String publisherName) throws Exception {
		List<Publisher> pubList = (List<Publisher>) read(
				"select * from tbl_publisher where publisherName = ?",
				new Object[] { publisherName });

		if (pubList != null && pubList.size() > 0) {
			return pubList.get(0);
		} else {
			return null;
		}
	}

	//
	@Override
	protected List<Publisher> extractData(ResultSet rs) throws SQLException {
		List<Publisher> pubList = new ArrayList<Publisher>();
		while (rs.next()) {
			Publisher p = new Publisher();
			p.setPublisherId(rs.getInt("publisherId"));
			p.setPublisherName(rs.getString("publisherName"));
			p.setPublisherAddress(rs.getString("publisherAddress"));
			p.setPublisherPhone(rs.getString("publisherPhone"));

			pubList.add(p);
		}
		return pubList;
	}

}