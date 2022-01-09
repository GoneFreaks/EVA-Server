package server;

import server.util.MessageManager;

public class IsAliveChecker implements Runnable{

	@Override
	public void run() {
		while(true) {
			try {
				StateManager.getClients().forEach((k) -> {
					MessageManager.sendMessage("#not", k);
				});
				Thread.sleep(5000);
			} catch (Exception e) {
			}
		}
	}

}
