package server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import server.util.Lobby;
import server.util.MessageManager;

public class StateManager {

	// Every client and his list of invitations
	private static ConcurrentHashMap<String, List<String>> connected = new ConcurrentHashMap<>();
	
	// Every client currently playing and the corresponding lobby
	private static ConcurrentHashMap<String, Lobby> lobbys = new ConcurrentHashMap<>();
	
	// Every client who has sent a request to another client
	private static List<String> requester = new ArrayList<>();
	
	// Every client and the corresponding socket
	private static Map<String, Socket> sockets = new ConcurrentHashMap<>();
	
	public static void addUser (String id, Socket client) {
		connected.put(id, new ArrayList<>());
		sockets.put(id, client);
		System.out.println("Neuer User: " + id);
	}
	
	public static String getData(String identifier) {
		
		if(connected.size() <= 0) return "NA";
		
		StringBuilder b = new StringBuilder("");
		List<String> list = connected.get(identifier);
		
		connected.forEach((k,v) -> {
			if(!requester.contains(k) && !lobbys.containsKey(k) && !identifier.equals(k) && !list.contains(k)) b.append((b.length() > 0? "," : "") + k);
		});
		
		if(b.length() < 1) b.append("-");
		b.append(",,");
		
		if(list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				b.append((i == 0? "" : ",") + list.get(i));
			}
		}
		else b.append("-");
		
		return b.toString();
	}
	
	public static boolean createLobby(String player1, String player2) {
		if(connected.containsKey(player1) && connected.containsKey(player2)) {
			Lobby lobby = new Lobby(player1, player2);
			lobbys.put(player1, lobby);
			requester.remove(player1);
			lobbys.put(player2, lobby);
			requester.remove(player2);
			return true;
		}
		else return false;
	}
	
	public static void addRequest(String send, String receive) {
		// Receiver is not playing, has not requested another client and is connected to the server
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
	
	private static void removeFromLobby(String id) {
		Lobby lobby;
		if((lobby = lobbys.remove(id)) != null) lobby.finishPlayer(id);
	}
	
	public static void reset(String id) {
		delete(id);
		connected.put(id, new ArrayList<>());
	}
	
	public static void delete(String id) {
		removeFromLobby(id);
		if(connected.get(id) != null) {
			connected.get(id).forEach((k) -> {
				if(connected.containsKey(k)) {
					MessageManager.sendMessage("#unl", k);
					requester.remove(k);
				}
			});
		}
		lobbys.remove(id);
		connected.remove(id);
		connected.forEach((k,v) -> {
			v.remove(id);
		});
		try {
			Socket temp;
			if((temp = sockets.remove(id)) != null) temp.close();
		} catch (Exception e) {
		}
	}
	
	public static Set<String> getClients(){
		return connected.keySet();
	}
	
	public static void shutdown() {
		sockets.forEach((k,v) -> {
			try {
				MessageManager.sendMessage("#del", k);
				v.close();
			} catch (Exception e) {
			}
		});
	}
	
}
