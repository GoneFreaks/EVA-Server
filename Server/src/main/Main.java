package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import db.ConnectionManager;
import server.CommandManager;
import server.MessageListenerManager;
import server.StateManager;
import server.listener.ConnectionListener;
import server.util.Filter;
import server.util.MessageManager;

public class Main {
	
	public static final int PORT = 9090;
	public static final int RANDOM_COUNT = 5;
	
	public static Main INSTANCE;
	public CommandManager cmdMan;
	
	public static void main(String[] args) {
		try {
			System.out.println("SERVER");
			Filter.filter();
			if(ConnectionManager.startUp()) {
				INSTANCE = new Main();
				INSTANCE.startup();
			}
			else System.out.println("VERBINDUNG ZUR DATENBANK KONNTE NICHT HERGESTELLT WERDEN");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void startup() {
		File log_file = new File("log.txt");
		if(log_file.exists()) log_file.delete();
		
		System.out.println("VERBINDUNG ZUR DATENBANK STEHT");
		
		cmdMan = new CommandManager();
		
		// Wait for new connection-request
		Thread connectionListener = new Thread(new ConnectionListener());
		connectionListener.setDaemon(true);
		connectionListener.start();
		System.out.println("CONNECTION-LISTENER WURDE GESTARTET");
		
		// Read new messages from clients
		MessageListenerManager.start();
		System.out.println("LISTENER WURDE GESTARTET");
		
		// Check if client is still available --> if available nothing happens else remove client from collections
		Thread checker = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					try {
						StateManager.getClients().forEach((k) -> {
							MessageManager.sendMessage("#not", k);
						});
						Thread.sleep(10000);
					} catch (Exception e) {
					}
				}
			}
		});
		checker.setDaemon(true);
		checker.start();
		System.out.println("CHECKER WURDE GESTARTET");
		
		shutdown();
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				try {
					ConnectionManager.shutdown();
					ConnectionListener.sock.close();
				} catch (Exception e) {
				}
			}
		});
		System.out.println("SERVER ONLINE");
	}
	
	private static void shutdown() {
		new Thread(() -> {

			String line = "";
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
				
				while ((line = reader.readLine()) != null) {
					if (line.equalsIgnoreCase("exit")) {
						System.exit(0);
					} else System.out.println("Use 'exit' to shutdown");
				}
				
			} catch (IOException ex) {
				ex.printStackTrace();
				System.exit(1);
			} 
		}).start();
	}
}
