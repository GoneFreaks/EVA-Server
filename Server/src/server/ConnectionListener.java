package server;

import java.net.ServerSocket;
import java.net.Socket;

import server.util.Identifier;
import server.util.MessageManager;
import server.util.Output;

/**
 * This listener waits for connection-requests</br>
 * If everything went successfull a new identifier will be assigned to the client</br>
 * The client and the necessary streams will be added to:
 * <ul>
 * <li>DataManager</li>
 * <li>MessageManager</li>
 * <li>Listener</li>
 * </ul>
 */
public class ConnectionListener implements Runnable {

	private static final int PORT = 9090;
	private Identifier identifier;
	
	public ConnectionListener() {
		this.identifier = new Identifier();
	}
	
	@Override
	public void run() {
		
		try (ServerSocket sock = new ServerSocket(PORT)) {
			while(true) {
				Socket connection = sock.accept();
				connection.setSoTimeout(5000);
				connection.setKeepAlive(true);
				String id = identifier.createIdentifier();
				StateManager.addUser(id, connection.getOutputStream());
				MessageManager.addUser(id, connection.getOutputStream());
				Listener.INSTANCE.addClient(id, connection.getInputStream());
			}
		} catch (Exception e) {
			Output.printException(e);
		}
	}
}
