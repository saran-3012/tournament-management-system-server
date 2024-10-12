package com.saran.tms.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

import com.saran.tms.config.DataBaseConfig;
import com.saran.tms.logger.ApplicationLogger;



public class ConnectionPool {
	
	final private static String URL = DataBaseConfig.getUrl();
	final private static String USERNAME = DataBaseConfig.getUser();
	final private static String PASSWORD = DataBaseConfig.getPassword();
	
	private static int minConnections;
	private static int maxConnections;
	private static AtomicInteger currentConnections = new AtomicInteger(0);
	
	private static BlockingQueue<Connection> connectionPool;


	private ConnectionPool(){}
	
	private static Connection createNewConnection() throws SQLException {
		currentConnections.incrementAndGet();
		return DriverManager.getConnection(URL, USERNAME, PASSWORD);
	}
	
	private static void closeConnection(Connection con) throws SQLException {
		con.close();
		currentConnections.decrementAndGet();
	}
	
	public static void initializeConnectionPool(int minSize, int maxSize) throws SQLException, ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
		connectionPool  = new ArrayBlockingQueue<>(maxSize);
		minConnections = minSize;
		maxConnections = maxSize;
		for(int i=0; i<minConnections; i++) {
			connectionPool.add(createNewConnection());
		}
	}
	
	public static Connection getConnection() throws InterruptedException, SQLException {
		if(connectionPool.isEmpty() && currentConnections.get() < maxConnections) {
			return createNewConnection();
		}
		return connectionPool.take();
	}
	
	public static void addExistingConnection(Connection con) throws InterruptedException {
		if (connectionPool.size() >= maxConnections) {
			try {
				closeConnection(con);
			} catch (SQLException e) {
				ApplicationLogger.log(Level.WARNING, "Unable to close a connection", e);
				e.printStackTrace();
			}
			return;
		}
		try {
			if (!con.isClosed()) {
				connectionPool.put(con);
			}
		} catch (SQLException e) {
			ApplicationLogger.log(Level.WARNING, "Unable to process the connection", e);
			e.printStackTrace();
		}
	}
	
	public synchronized static void fillVacantConnections() throws SQLException {
		while(connectionPool.size() < maxConnections) {
			connectionPool.add(createNewConnection());
		}  
	}
	
	public synchronized static void removeClosedConnections() throws SQLException {
		for(int availableConnection = connectionPool.size(); availableConnection > 0; availableConnection--) {
			Connection con = connectionPool.poll();
			if(!con.isClosed()) {
				connectionPool.add(con);
			}
		}
	}
	
	public synchronized static void closeAllConnections() throws SQLException {
		while(!connectionPool.isEmpty()) {
				closeConnection(connectionPool.poll());
		}
	}
	
}