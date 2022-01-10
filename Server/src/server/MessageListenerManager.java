package server;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import server.util.MessageManager;

/**
 * This listener stores every user inside a collection</br>
 * After a short period of time, every InputStream gets checked if somethings has been sent to the server
 */
public class MessageListenerManager{

	public static MessageListenerManager INSTANCE;
	
	private static Map <Integer, Map<String, InputStream>> storage = new ConcurrentHashMap<>();
	private static int thread_count;
	
	public MessageListenerManager() {
		INSTANCE = this;
		thread_count = (int) Math.max(1, Runtime.getRuntime().availableProcessors()/2);
		for (int i = 0; i < thread_count; i++) {
			storage.put(i, new ConcurrentHashMap<>());
		}
	}
	
	public void start() {
		for (int i = 0; i < thread_count; i++) {
			Thread temp = new Thread(new MessageListener(i));
			temp.setDaemon(true);
			temp.setName("MessageListener-" + i);
			temp.start();
		}
	}

	public synchronized void addClient(String identifier, InputStream in) {
		int min = Integer.MAX_VALUE;
		int index = 0;
		for (int i = 0; i < storage.size(); i++) {
			if(storage.get(i).size() < min) {
				index = i;
				min = storage.get(i).size();
			}
		}
		storage.get(index).put(identifier, in);
		MessageManager.sendMessage("#get" + identifier, identifier);
	}
	
	public synchronized void removeClient(String identifier) {
		storage.forEach((k,v) -> {
			v.remove(identifier);
		});
	}
	
	public synchronized Map<String, InputStream> getCertainMap(int id) {
		return storage.get(id);
	}
	
}
