package server;

import java.io.InputStream;

public class MessageListener implements Runnable {

	private int id;
	
	public MessageListener(int id) {
		this.id = id;
	}
	
	@Override
	public void run() {
		while(true) {
			MessageListenerManager.INSTANCE.getCertainMap(id).forEach((k,v) -> {
				String[] read = readInputStream(v);
				if(read != null) {
					for(int i = 0; i < read.length; i++) {
						CommandManager.INSTANCE.performCommand(k, read[i]);
					}
				}
			});
			try {
				Thread.sleep(100);
			} catch (Exception e) {
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
