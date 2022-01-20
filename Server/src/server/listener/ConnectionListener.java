package server.listener;

import java.net.ServerSocket;
import java.net.Socket;

import server.StateManager;
import server.util.IdManager;
import server.util.MessageManager;

public class ConnectionListener {

	private ServerSocket server;
	private boolean waiting;
	
	public ConnectionListener (ServerSocket server) {
		this.server = server;
		this.waiting = false;
	}
	
	public void awaitRequest() {
		try {
			Socket connection;
			
			synchronized (this) {
				while(waiting) wait();
				waiting = true;
				
				connection = server.accept();
				
				waiting = false;
				notifyAll();
				
			}
			
			connection.setSoTimeout(5000);
			connection.setKeepAlive(true);
			String id = IdManager.createIdentifier();
			StateManager.addUser(id, connection);
			MessageManager.addUser(id, connection.getOutputStream(), connection.getInputStream());
			MessageListenerManager.addClient(id);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
