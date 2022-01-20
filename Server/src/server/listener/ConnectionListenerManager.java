package server.listener;

import java.net.ServerSocket;

import server.Main;

/**
 * Depending on the number of available processors, this manager will use n/2 Threads to wait for connection-requests
 */
public class ConnectionListenerManager {

	private static ServerSocket server;
	
	public static void start() {
		try {
			server = new ServerSocket(Main.PORT);
			ConnectionListener listener = new ConnectionListener(server);
			Runnable task = () -> {while(true) listener.awaitRequest();};
			int thread_count = (int) Math.max(1, Runtime.getRuntime().availableProcessors() / 2);
			
			for (int i = 0; i < thread_count; i++) {
				Thread temp = new Thread(task);
				temp.setDaemon(true);
				temp.setName("ConnectionListener-" + i);
				temp.start();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void shutdown() {
		try {
			server.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
