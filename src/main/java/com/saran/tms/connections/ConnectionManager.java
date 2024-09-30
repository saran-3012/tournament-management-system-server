package com.saran.tms.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import com.saran.tms.config.DataBaseConfig;

public class ConnectionManager {
	final private static String URL = DataBaseConfig.getUrl();
	final private static String USERNAME = DataBaseConfig.getUser();
	final private static String PASSWORD = DataBaseConfig.getPassword();
	static {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USERNAME, PASSWORD);
	}
	
	public static void closeConnection(Connection con) throws SQLException {
		con.close();
	}
}
