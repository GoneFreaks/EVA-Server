package server;

import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

import main.Output;

public class DataManager {

	private static ConcurrentHashMap<Socket, String> connected_sockets = new ConcurrentHashMap<>();
	private static ConcurrentHashMap<Socket, Lobby> lobbys = new ConcurrentHashMap<>();
	
	public static void addSocket (Socket socket, String username) {
		connected_sockets.put(socket, username);
		Output.print(username);
	}
	
	public static String mapToString() {
		StringBuilder b = new StringBuilder("");
		connected_sockets.forEach((k,v) -> {
			b.append(v + (lobbys.containsKey(k)? " playing" : " waiting") + "\n");
		});
		return b.toString();
	}
	
}
