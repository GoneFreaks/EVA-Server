package server.listener;

import server.Main;
import server.util.MessageManager;

public class MessageListener implements Runnable {

	private int id;
	
	public MessageListener(int id) {
		this.id = id;
	}
	
	@Override
	public void run() {
		while(true) {
			MessageListenerManager.getList(id).forEach((k) -> {
				String[] read = MessageManager.readMessage(k);
				if(read != null) {
					for(int i = 0; i < read.length; i++) {
						Main.INSTANCE.cmdMan.performCommand(k, read[i]);
					}
				}
			});
			try {
				Thread.sleep(100);
			} catch (Exception e) {
			}
		}
	}
}
