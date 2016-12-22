package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

	public static Connection getConnection() throws ClassNotFoundException,
	SQLException {
		String driver = "com.mysql.jdbc.Driver";
		String connection = "jdbc:mysql://localhost:2579/library";
		String user = "root";
		String password = "oogieboogie";

		Class.forName(driver);
		Connection con = DriverManager
				.getConnection(connection, user, password);
		con.setAutoCommit(false);

		return con;
	}
}