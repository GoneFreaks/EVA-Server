package server;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import server.util.Output;

public class DataManager {

	private static ConcurrentHashMap<String, List<String>> connected = new ConcurrentHashMap<>();
	private static ConcurrentHashMap<String, Lobby> lobbys = new ConcurrentHashMap<>();
	
	public static void addUser (String id, PrintWriter out) {
		connected.put(id, new ArrayList<>());
		MessageManager.addUser(id, out);
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
	
	public static void createLobby(String player1, String player2) {
		Lobby lobby = new Lobby(player1, player2);
		lobbys.put(player1, lobby);
		lobbys.put(player2, lobby);
	}
	
	public static void addRequest(String send, String receive) {
		connected.get(receive).add(send);
	}
	
}
