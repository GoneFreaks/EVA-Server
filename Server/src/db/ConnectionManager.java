package db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionManager {
	
	private static Connection connection;
	
	public static Connection getConnection() {
		return connection;
	}
	
	public static void shutdown() {
		try {
			if(connection != null) {
				connection.close();
				System.out.println("VERBINDUNG GESCHLOSSEN");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean startUp() {
		
		try {
			File file = new File("data.db");
			
			if(!file.exists()) return false;
			String url = "jdbc:sqlite:" + file.getPath();
			connection = DriverManager.getConnection(url);
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}	
	}
	
}
