package server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import server.util.Output;

public class DataManager {

	private static ConcurrentHashMap<String, List<String>> connected = new ConcurrentHashMap<>();
	
	public static void addUser (String id) {
		connected.put(id, new ArrayList<>());
		Output.print("Neuer User: " + id);
	}
	
	public static String getData(String identifier) {
		if(connected.size() <= 0) return "NA";
		StringBuilder b = new StringBuilder("");
		connected.forEach((k, v) -> {
			b.append((b.length() > 0? "," : "") + k);
		});
		List<String> list = connected.get(identifier);
		if(list.size() > 0) {
			b.append(",,");
			for (int i = 0; i < list.size(); i++) {
				b.append((i == 0? "" : ",") + list.get(0));
			}
		}
		return b.toString();
	}
	
	public static void addRequest(String send, String receive) {
		connected.get(receive).add(send);
	}
	
}
