package server.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientMap {

	private Map<Integer, List<String>> storage = new ConcurrentHashMap<>();
	
	public ClientMap(int size) {
		for (int i = 0; i < size; i++) {
			storage.put(i, new LinkedList<>());
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
		printDebugOutput();
	}
	
	public synchronized void removeClient(String identifier) {
		storage.forEach((k,v) -> {
			if(v.remove(identifier)) rebalanceLists(k);
		});
		printDebugOutput();
	}
	
	private void rebalanceLists(int id) {
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
	
	public synchronized List<String> getList(int id) {
		return storage.get(id);
	}
	
	private void printDebugOutput() {
		storage.forEach((k,v) -> {
			Output.print(v.size() + " ");
		});
		Output.println("");
	}
	
}
