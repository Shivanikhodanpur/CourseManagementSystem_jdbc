package com.digit.CRSApp.services;

import java.sql.Connection;
import java.sql.DriverManager;

public class Services {

	private static Connection conn;
	private static String userName = "root";
	private static String password = "Shivsai@5";
	private static String url = "jdbc:mysql://localhost:3306/crs";

	public static Connection getConnectionInstance() {
		if (conn == null) {
			try {
				conn = DriverManager.getConnection(url, userName, password);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return conn;
	}

}
