package server;

import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;

public class MessageManager {

	private static ConcurrentHashMap<String, PrintWriter> writer = new ConcurrentHashMap<>();
	
	public static void addUser(String id, PrintWriter out) {
		writer.put(id, out);
	}
	
	public static void sendMessage(String message, String id) {
		writer.get(id).println(message);
	}
	
}
