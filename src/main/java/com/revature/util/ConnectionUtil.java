package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	
	public static Connection connection;
	
	public static Connection getConnection() throws SQLException {
		
		String url = System.getenv("POSTGRES_URL");
		String username = System.getenv("POSTGRES_USER");
		String password = System.getenv("POSTGRES_PASSWORD");
		connection = DriverManager.getConnection(url, username, password);
		return connection;
	}
}