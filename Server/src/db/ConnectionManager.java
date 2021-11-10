package db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.Output;

public class ConnectionManager {

	private static Connection connection;
	
	public static synchronized Connection getConnection() {
		return connection;
	}
	
	public static void shutdown() {
		try {
			if(connection != null) {
				connection.close();
				Output.print("VERBINDUNG GESCHLOSSEN");
			}
		} catch (Exception e) {
			Output.printException(e);
		}
	}
	
	public static boolean startUp() {
		
		try {
			File file = new File("data.db");
			
			boolean new_file = false;
			if(!file.exists()) {
				file.createNewFile();
				new_file = true;
			}
			String url = "jdbc:sqlite:" + file.getPath();
			connection = DriverManager.getConnection(url);
			if(new_file) {
				createDefaultTables();
				populateDefaultTables();
			}
			return true;
			
		} catch (Exception e) {
			Output.printException(e);
			return false;
		}	
	}
	
	private static void createDefaultTables() throws SQLException {
		List<String> querys = readData("create_default_tables.txt");
		executeDefaultQuerys(querys);
	}
	
	private static void populateDefaultTables() throws SQLException {
		List<String> data = readData("data_default_tables.txt");
		executeDefaultQuerys(data);
	}
	
	private static List<String> readData (String file) throws SQLException {
		List<String> result = new ArrayList<>();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(file))){
			String temp;
			while((temp = reader.readLine()) != null) result.add(temp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	private static void executeDefaultQuerys(List<String> querys) throws SQLException {
		try(Statement stmt = connection.createStatement()) {
			for (String i : querys) {
				stmt.addBatch(i);
			}
			stmt.executeBatch();
		}
	}
	
}
