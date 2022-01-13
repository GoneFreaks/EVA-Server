package server;

import java.util.List;

import server.util.ClientMap;

/**
 * Depending on the number of available processors, this manager will use n Threads to read from all clients
 */
public class MessageListenerManager{

	public static MessageListenerManager INSTANCE;
	private int size;
	private ClientMap map;
	
	public MessageListenerManager() {
		INSTANCE = this;
		this.size = Runtime.getRuntime().availableProcessors();
		this.map = new ClientMap(size);
	}
	
	public void start() {
		for (int i = 0; i < size; i++) {
			Thread temp = new Thread(new MessageListener(i));
			temp.setDaemon(true);
			temp.setName("MessageListener-" + i);
			temp.start();
		}
	}

	public void addClient(String identifier) {
		map.addClient(identifier);
	}
	
	public void removeClient(String identifier) {
		map.removeClient(identifier);
	}
	
	public List<String> getList(int id) {
		return map.getList(id);
	}
	
}
