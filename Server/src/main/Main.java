package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import db.ConnectionManager;
import server.CommandManager;
import server.ConnectionListener;
import server.util.Output;

public class Main {
	
	public static void main(String[] args) {
		try {
			Output.checkOutput();
			if(ConnectionManager.startUp()) {
				File log_file = new File("log.txt");
				if(log_file.exists()) log_file.delete();
				Output.print("VERBINDUNG ZUR DATENBANK STEHT");
				Thread listener = new Thread(new ConnectionListener(new CommandManager()));
				listener.setName("Listener");
				listener.setDaemon(true);
				listener.start();
				Output.print("LISTENER WURDE GESTARTET");
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
						break;
					} else Output.print("Use 'exit' to shutdown");
				}
				
			} catch (IOException ex) {
				Output.printException(ex);
			} 
		}).start();
	}

}
