package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import main.Output;

public class Listener implements Runnable {

	private static final int PORT = 9090;
	private CommandManager cmdMan;
	
	public Listener(CommandManager cmdMan) {
		this.cmdMan = cmdMan;
	}
	
	@Override
	public void run() {
		
		try (ServerSocket sock = new ServerSocket(PORT)) {
			while(true) {
				Socket connection = sock.accept();
				connection.setSoTimeout(0);
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				if((line = reader.readLine()) != null) cmdMan.performCommand(connection, line);
			}
		} catch (Exception e) {
			Output.printException(e);
		}
	}
}
