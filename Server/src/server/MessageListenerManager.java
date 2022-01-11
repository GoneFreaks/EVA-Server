package server;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import server.util.MessageManager;
import server.util.Output;

/**
 * Depending on the number of available processors, this manager will use n Threads to read from all clients
 */
public class MessageListenerManager{

	public static MessageListenerManager INSTANCE;
	
	private static Map <Integer, List<String>> storage = new ConcurrentHashMap<>();
	private static int thread_count;
	
	public MessageListenerManager() {
		INSTANCE = this;
		thread_count = Runtime.getRuntime().availableProcessors();
		for (int i = 0; i < thread_count; i++) {
			storage.put(i, new LinkedList<>());
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

	public synchronized void addClient(String identifier) {
		int min = Integer.MAX_VALUE;
		int index = 0;
		for (int i = 0; i < storage.size(); i++) {
			if(storage.get(i).size() < min) {
				index = i;
				min = storage.get(i).size();
			}
		}
		storage.get(index).add(identifier);
		MessageManager.INSTANCE.sendMessage("#get" + identifier, identifier);
		
		// Only debugging
		storage.forEach((k,v) -> {
			Output.print(v.size() + " ");
		});
		Output.println("");
	}
	
	public synchronized void removeClient(String identifier) {
		storage.forEach((k,v) -> {
			v.remove(identifier);
		});
	}
	
	public synchronized List<String> getCertainList(int id) {
		return storage.get(id);
	}
	
}
