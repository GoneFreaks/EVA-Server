package server;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import server.util.MessageManager;
import server.util.Output;

/**
 * This manager is responsible for the "state" of each client</br>
 * Each state is represented by certain collections</br>
 * For instance if a client requests another client, the client gets a new state called requester
 */
public class DataManager {

	private static ConcurrentHashMap<String, List<String>> connected = new ConcurrentHashMap<>();
	private static ConcurrentHashMap<String, Lobby> lobbys = new ConcurrentHashMap<>();
	private static List<String> requester = new ArrayList<>();
	
	public static void addUser (String id, OutputStream out) {
		connected.put(id, new ArrayList<>());
		Output.print("Neuer User: " + id);
	}
	
	public static String getData(String identifier) {
		
		if(connected.size() <= 0) return "NA";
		
		StringBuilder b = new StringBuilder("");
		List<String> list = connected.get(identifier);
		
		connected.forEach((k,v) -> {
			if(!requester.contains(k) && !lobbys.containsKey(k) && !identifier.equals(k) && !list.contains(k)) b.append((b.length() > 0? "," : "") + k);
		});
		
		if(list.size() > 0) {
			b.append(",,");
			for (int i = 0; i < list.size(); i++) {
				b.append((i == 0? "" : ",") + list.get(i));
			}
		}
		return b.toString();
	}
	
	public static void createLobby(String player1, String player2) {
		Lobby lobby = new Lobby(player1, player2);
		lobbys.put(player1, lobby);
		requester.remove(player1);
		lobbys.put(player2, lobby);
		requester.remove(player2);
	}
	
	public static void addRequest(String send, String receive) {
		if(!lobbys.containsKey(receive) && !requester.contains(receive) && connected.containsKey(receive)) {
			connected.get(receive).add(send);
			requester.add(send);
		}
		else MessageManager.sendMessage("#unl", send);
	}
	
	public static Lobby getLobby(String id) {
		return lobbys.get(id);
	}
	
	public static void clearUserRequests(String identifier, String acceptedUser) {
		connected.get(identifier).forEach((k) -> {
			if(!acceptedUser.equals(k)) {
				MessageManager.sendMessage("#unl", k);
				requester.remove(k);
			}
		});
	}
	
	public static void reset(String id) {
		delete(id);
		connected.put(id, new ArrayList<>());
	}
	
	public static void delete(String id) {
		connected.get(id).forEach((k) -> {
			if(connected.containsKey(k)) {
				MessageManager.sendMessage("#unl", k);
				requester.remove(k);
			}
		});
		lobbys.remove(id);
		connected.remove(id);
	}
	
}
