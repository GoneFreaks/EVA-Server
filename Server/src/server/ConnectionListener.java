package server;

import java.net.ServerSocket;
import java.net.Socket;

import main.Main;
import server.util.Identifier;
import server.util.MessageManager;

public class ConnectionListener implements Runnable {
	public static ServerSocket sock;
	
	@Override
	public void run() {
		try {
			sock = new ServerSocket(Main.PORT);
			while(true) {
				Socket connection = sock.accept();
				connection.setSoTimeout(5000);
				connection.setKeepAlive(true);
				String id = Identifier.INSTANCE.createIdentifier();
				StateManager.addUser(id, connection);
				MessageManager.INSTANCE.addUser(id, connection.getOutputStream(), connection.getInputStream());
				MessageListenerManager.INSTANCE.addClient(id);
			}
		} catch (Exception e) {
		}
	}
}
