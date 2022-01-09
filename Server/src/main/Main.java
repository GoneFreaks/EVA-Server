package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import db.ConnectionManager;
import server.CommandManager;
import server.ConnectionListener;
import server.IsAliveChecker;
import server.MessageListener;
import server.util.Identifier;
import server.util.Output;

public class Main {
	
	public static void main(String[] args) {
		try {
			Output.checkOutput();
			if(ConnectionManager.startUp()) {
				File log_file = new File("log.txt");
				if(log_file.exists()) log_file.delete();
				Output.print("VERBINDUNG ZUR DATENBANK STEHT");
				
				new CommandManager();
				new Identifier();
				
				Thread connectionListener = new Thread(new ConnectionListener());
				connectionListener.setDaemon(true);
				connectionListener.start();
				Output.print("CONNECTION-LISTENER WURDE GESTARTET");
				
				Thread listener = new Thread(new MessageListener());
				listener.setDaemon(true);
				listener.start();
				Output.print("LISTENER WURDE GESTARTET");
				
				Thread checker = new Thread(new IsAliveChecker());
				checker.setDaemon(true);
				checker.start();
				Output.print("CHECKER WURDE GESTARTET");
				
				shutdown();
				System.out.println("SERVER ONLINE");
			}
			else Output.print("VERBINDUNG ZUR DATENBANK KONNTE NICHT HERGESTELLT WERDEN");
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
						ConnectionManager.shutdown();
						System.exit(0);
					} else Output.print("Use 'exit' to shutdown");
				}
				
			} catch (IOException ex) {
				Output.printException(ex);
				System.exit(1);
			} 
		}).start();
	}

}
