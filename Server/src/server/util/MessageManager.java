package server.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ConcurrentHashMap;

import server.CommandManager;

public class MessageManager {

	private static ConcurrentHashMap<String, OutputStream> writer = new ConcurrentHashMap<>();
	private static ConcurrentHashMap<String, InputStream> reader = new ConcurrentHashMap<>();
	
	public static MessageManager INSTANCE;
	
	public MessageManager() {
		INSTANCE = this;
	}
	
	public synchronized void removeId(String id) {
		writer.remove(id);
		reader.remove(id);
	}
	
	public synchronized void addUser(String id, OutputStream out, InputStream in) {
		writer.put(id, out);
		reader.put(id, in);
	}
	
	public synchronized void sendMessage(String message, String id) {
		byte[] output = message.getBytes();
		try {
			OutputStream out = writer.get(id);
			if(out != null) {
				out.write(output);
				out.flush();
			}
		} catch (Exception e) {
			Output.printException(e);
			CommandManager.INSTANCE.performCommand(id, "del");
		}
	}
	
	public synchronized String[] readMessage(String id) {
		try {
			InputStream in = reader.get(id);
			if(in != null && in.available() > 0) {
				byte[] arr = new byte[in.available()];
				in.read(arr, 0, in.available());
				StringBuilder b = new StringBuilder("");
				for (int i = 0; i < arr.length; i++) {
					b.append((char) arr[i]);
				}
				return b.toString().substring(1).split("#");
			}
			else return null;
		} catch (Exception e) {
			return null;
		}
	}
	
}
