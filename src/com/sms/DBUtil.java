package com.sms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	// change these to your DB settings
	private static final String URL = "jdbc:mysql://localhost:3306/sms_db?serverTimezone=UTC";
	private static final String USER = "root";
	private static final String PASS = "";

	static {
		try {
			// optional with modern drivers, but safe to include
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASS);
	}
}
