package server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import server.util.Lobby;
import server.util.MessageManager;

public class StateManager {

	// Every client and his list of invitations
	private static ConcurrentHashMap<String, List<String>> connected = new ConcurrentHashMap<>();
	
	// Every client currently playing and his lobby
	private static ConcurrentHashMap<String, Lobby> lobbys = new ConcurrentHashMap<>();
	
	// Ever client who has sent a request to another client
	private static List<String> requester = new ArrayList<>();
	
	private static ConcurrentHashMap<String, Socket> socket_map = new ConcurrentHashMap<>();
	
	public static void addUser (String id, Socket client) {
		connected.put(id, new ArrayList<>());
		socket_map.put(id, client);
		System.out.println("Neuer User: " + id);
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
	
	private static void removeFromLobby(String id) {
		Lobby lobby;
		if((lobby = lobbys.remove(id)) != null) {
			lobby.finishPlayer(id);
		}
	}
	
	public static void reset(String id) {
		delete(id, false);
		connected.put(id, new ArrayList<>());
	}
	
	public static void delete(String id, boolean delete) {
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
		if(delete) {
			try {
				socket_map.remove(id).close();
			} catch (Exception e) {
			}
		}
	}
	
	public static Set<String> getClients(){
		return connected.keySet();
	}
	
	public static void closeAll() {
		socket_map.forEach((k,v) -> {
			try {
				MessageListenerManager.removeClient(k);
				v.close();
			} catch (Exception e) {
			}
		});
	}
	
}
