package server;

import java.io.OutputStream;
import java.util.concurrent.ConcurrentHashMap;

import server.util.Output;

public class MessageManager {

	private static ConcurrentHashMap<String, OutputStream> writer = new ConcurrentHashMap<>();
	
	public static void removeId(String id) {
		writer.remove(id);
	}
	
	public static void addUser(String id, OutputStream out) {
		writer.put(id, out);
	}
	
	public static void sendMessage(String message, String id) {
		byte[] output = message.getBytes();
		try {
			OutputStream out = writer.get(id);
			out.write(output);
			out.flush();
			
		} catch (Exception e) {
			Output.printException(e);
		}
	}
	
}
