package server;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import main.Output;

public class DataManager {

	private static Set<String> connected_sockets = ConcurrentHashMap.newKeySet();
	private static ConcurrentHashMap<String, Lobby> lobbys = new ConcurrentHashMap<>();
	
	public static void addUser (String id) {
		connected_sockets.add(id);
		Output.print("Neuer User: " + id);
	}
	
	public static String mapToString() {
		StringBuilder b = new StringBuilder("");
		connected_sockets.forEach((k) -> {
			b.append(k + (lobbys.containsKey(k)? " playing" : " waiting") + "\n");
		});
		if(connected_sockets.size() <= 0) b.append("NO PLAYERS AVAILABLE");
		return b.toString();
	}
	
}
