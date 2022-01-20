package server.listener;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import server.util.MessageManager;

/**
 * Depending on the number of available processors, this manager will use n Threads to read from all clients
 */
public class MessageListenerManager{

	private static int size;
	private static Map<Integer, List<String>> storage = new ConcurrentHashMap<>();
	
	public static void start() {
		size = Runtime.getRuntime().availableProcessors();
		for (int i = 0; i < size; i++) {
			storage.put(i, new LinkedList<>());
		}
		
		for (int i = 0; i < size; i++) {
			Thread temp = new Thread(new MessageListener(i));
			temp.setDaemon(true);
			temp.setName("MessageListener-" + i);
			temp.start();
		}
	}

	public static void addClient(String identifier) {
		int min = Integer.MAX_VALUE;
		int index = 0;
		for (int i = 0; i < storage.size(); i++) {
			if(storage.get(i).size() < min) {
				index = i;
				min = storage.get(i).size();
			}
		}
		storage.get(index).add(identifier);
		MessageManager.sendMessage("#get" + identifier, identifier);
		printDebugOutput();
	}
	
	public static void removeClient(String identifier) {
		storage.forEach((k,v) -> {
			if(v.remove(identifier)) rebalanceLists(k);
		});
		printDebugOutput();
	}
	
	public static List<String> getList(int id) {
		return storage.get(id);
	}
	
	//ensure that nothing like this happens: 0 8 0 1 2 0 3 6
	// number of clients per thread
	private static void rebalanceLists(int id) {
		int max = Integer.MIN_VALUE;
		int max_index = 0;
		for (int i = 0; i < storage.size(); i++) {
			if(storage.get(i).size() > max) {
				max_index = i;
				max = storage.get(i).size();
			}
		}
		if(storage.get(id).size() + 2 <= storage.get(max_index).size()) {
			storage.get(id).add(storage.get(max_index).remove(0));
		}
	}
	
	private static void printDebugOutput() {
		storage.forEach((k,v) -> {
			System.out.print(v.size() + " ");
		});
		System.out.println();
	}
	
}
