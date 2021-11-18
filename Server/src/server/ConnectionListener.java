package server;

import java.net.ServerSocket;
import java.net.Socket;

import server.util.Identifier;
import server.util.Output;

public class ConnectionListener implements Runnable {

	private static final int PORT = 9090;
	private CommandManager cmdMan;
	private Identifier identifier;
	
	public ConnectionListener(CommandManager cmdMan) {
		this.cmdMan = cmdMan;
		this.identifier = new Identifier();
	}
	
	@Override
	public void run() {
		
		try (ServerSocket sock = new ServerSocket(PORT)) {
			while(true) {
				Socket connection = sock.accept();
				connection.setSoTimeout(0);
				connection.setKeepAlive(true);
				String id = identifier.createIdentifier();
				DataManager.addUser(id);
				Thread msgListener = new Thread(new MessageListener(connection.getInputStream(), connection.getOutputStream(), cmdMan, id));
				msgListener.setDaemon(true);
				msgListener.start();
			}
		} catch (Exception e) {
			Output.printException(e);
		}
	}
}
