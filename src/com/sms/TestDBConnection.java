package com.sms;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestDBConnection {
	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/sms_db?serverTimezone=UTC";
		String user = "root"; // or sms_user
		String pass = ""; // or your password

		try (Connection conn = DriverManager.getConnection(url, user, pass)) {
			if (conn != null && !conn.isClosed()) {
				System.out.println("Connected to DB successfully!");
			} else {
				System.out.println("Failed to connect.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
