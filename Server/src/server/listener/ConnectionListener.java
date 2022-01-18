package server.listener;

import java.net.ServerSocket;
import java.net.Socket;

import main.Main;
import server.MessageListenerManager;
import server.StateManager;
import server.util.IdManager;
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
				String id = IdManager.createIdentifier();
				StateManager.addUser(id, connection);
				MessageManager.addUser(id, connection.getOutputStream(), connection.getInputStream());
				MessageListenerManager.addClient(id);
			}
		} catch (Exception e) {
		}
	}
}
