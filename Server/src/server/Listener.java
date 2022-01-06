package server;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import server.util.MessageManager;

public class Listener implements Runnable{

	private static Map<String, InputStream> storage = new ConcurrentHashMap<>();
	public static Listener INSTANCE;
	
	public Listener() {
		INSTANCE = this;
	}
	
	public synchronized void addClient(String identifier, InputStream in) {
		storage.put(identifier, in);
		MessageManager.sendMessage("#get" + identifier, identifier);
	}
	
	public synchronized void removeClient(String identifier) {
		storage.remove(identifier);
	}

	@Override
	public void run() {
		while(true) {
			synchronized (this){
				storage.forEach((k,v) -> {
					String[] read = readInputStream(storage.get(k));
					if(read != null) {
						for(int i = 0; i < read.length; i++) {
							CommandManager.INSTANCE.performCommand(k, read[i]);
						}
					}
				});
			}
			try {
				Thread.sleep(50);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private String[] readInputStream (InputStream in) {
		try {
			if(in.available() > 0) {
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
