package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class BaseDAO<T> {
	private Connection connection = null; 
	public BaseDAO(Connection conn) {
		this.connection = conn;
	}
	
	private Integer pageNo;
	private Integer pageSize = 10;

	protected Connection getConnection() {
		return connection;
	}

	public void save(String query, Object[] vals) throws Exception {
		Connection con = getConnection();

		PreparedStatement stmt = con.prepareStatement(query);
		int count = 1;
		for (Object o : vals) {
			stmt.setObject(count, o);
			count++;
		}

		stmt.executeUpdate();
	}
	
	// Insert record into table and returnt auto generated field i.e ID of the record
	public int saveWithId(String query, Object[] vals) throws Exception {
		Connection con = getConnection();

		PreparedStatement stmt = con.prepareStatement(query,
				PreparedStatement.RETURN_GENERATED_KEYS);
		int count = 1;
		for (Object o : vals) {
			stmt.setObject(count, o);
			count++;
		}

		stmt.executeUpdate();
		ResultSet rs = stmt.getGeneratedKeys();
		if (rs.next())
			return rs.getInt(1);
		else
			return -1;

	}
	
	// Return list of all records of a table 
	public List<?> read(String query, Object[] vals) throws Exception {
		Connection conn = getConnection();
		PreparedStatement stmt = conn.prepareStatement(query);

		if (vals != null) {
			int count = 1;
			for (Object o : vals) {
				stmt.setObject(count, o);
				count++;
			}
		}

		ResultSet rs = stmt.executeQuery();
		return extractData(rs);
	}
	
	
	public <T>List<T> readAll(String query, Object[] vals) throws SQLException{/////////////////////
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		if(getPageNo()!=null && getPageNo() > 0){
			System.out.println(getPageNo());
			Integer limit = (getPageNo()-1) * getPageSize();
			query+=" LIMIT "+limit+" ,"+getPageSize();
		}
		try {
			pstmt = conn.prepareStatement(query);
			if(vals!=null){
				int count=1;
				for(Object o: vals){
					pstmt.setObject(count, o);
					count++;
				}
			}
			ResultSet rs = pstmt.executeQuery();
			return (List<T>) extractData(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public abstract <T>List<T> extractData(ResultSet rs);

	// Exrtact data from result set after reading data from database
	// saves each record in table into a list of objects 
	// dependant on the calling object's DAO
//	protected abstract List<?> extractData(ResultSet rs) throws SQLException, Exception;
	
	public Integer getCount(String query, Object[] vals) throws SQLException{
		Connection conn = getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(query);
			if(vals!=null){
				int count=1;
				for(Object o: vals){
					pstmt.setObject(count, o);
					count++;
				}
			}
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				return rs.getInt("COUNT");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @return the pageNo
	 */
	public Integer getPageNo() {
		return pageNo;
	}

	/**
	 * @param pageNo the pageNo to set
	 */
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * @return the pageSize
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}