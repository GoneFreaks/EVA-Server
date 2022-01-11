package db;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

import server.util.Output;

public class ConnectionManager {
	
	private static Connection connection;
	
	public static synchronized Connection getConnection() {
		return connection;
	}
	
	public static void shutdown() {
		try {
			if(connection != null) {
				connection.close();
				Output.println("VERBINDUNG GESCHLOSSEN");
			}
		} catch (Exception e) {
			Output.printException(e);
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
			Output.printException(e);
			return false;
		}	
	}
	
}
