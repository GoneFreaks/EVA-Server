package server;

import server.util.MessageManager;

public class MessageListener implements Runnable {

	private int id;
	
	public MessageListener(int id) {
		this.id = id;
	}
	
	@Override
	public void run() {
		while(true) {
			MessageListenerManager.INSTANCE.getCertainList(id).forEach((k) -> {
				String[] read = MessageManager.INSTANCE.readMessage(k);
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
}
