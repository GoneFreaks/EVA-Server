package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import db.ConnectionManager;
import server.CommandManager;
import server.ConnectionListener;
import server.MessageListenerManager;
import server.StateManager;
import server.util.Identifier;
import server.util.MessageManager;
import server.util.Output;

public class Main {
	
	public static final int PORT = 9090;
	public static final int RANDOM_COUNT = 2;
	
	public static void main(String[] args) {
		try {
			Output.checkOutput();
			if(ConnectionManager.startUp()) {
				
				File log_file = new File("log.txt");
				if(log_file.exists()) log_file.delete();
				
				Output.println("VERBINDUNG ZUR DATENBANK STEHT");
				
				new CommandManager();
				new Identifier();
				new MessageManager();
				
				// Wait for new connection-request
				Thread connectionListener = new Thread(new ConnectionListener());
				connectionListener.setDaemon(true);
				connectionListener.start();
				Output.println("CONNECTION-LISTENER WURDE GESTARTET");
				
				// Read new messages from clients
				new MessageListenerManager().start();
				Output.println("LISTENER WURDE GESTARTET");
				
				// Check if client is still available --> if available nothing happens else remove client from collections
				Thread checker = new Thread(new Runnable() {
					@Override
					public void run() {
						while(true) {
							try {
								StateManager.getClients().forEach((k) -> {
									MessageManager.INSTANCE.sendMessage("#not", k);
								});
								Thread.sleep(10000);
							} catch (Exception e) {
							}
						}
					}
				});
				checker.setDaemon(true);
				checker.start();
				Output.println("CHECKER WURDE GESTARTET");
				
				shutdown();
				Runtime.getRuntime().addShutdownHook(new Thread() {
					@Override
					public void run() {
						try {
							ConnectionManager.shutdown();
							StateManager.closeAll();
							ConnectionListener.sock.close();
						} catch (Exception e) {
						}
					}
				});
				System.out.println("SERVER ONLINE");
			}
			else Output.println("VERBINDUNG ZUR DATENBANK KONNTE NICHT HERGESTELLT WERDEN");
		} catch (Exception e) {
			Output.printException(e);
		}
	}
	
	private static void shutdown() {
		new Thread(() -> {

			String line = "";
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
				
				while ((line = reader.readLine()) != null) {
					if (line.equalsIgnoreCase("exit")) {
						System.exit(0);
					} else Output.println("Use 'exit' to shutdown");
				}
				
			} catch (IOException ex) {
				Output.printException(ex);
				System.exit(1);
			} 
		}).start();
	}
}
