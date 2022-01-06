package server;

import java.io.InputStream;

public class MessageListener implements Runnable{

	private String identifier;
	
	private InputStream in;
	private CommandManager cmdMan;
	
	public MessageListener(InputStream in, CommandManager cmdMan, String identifier) {
		this.in = in;
		this.cmdMan = cmdMan;
		this.identifier = identifier;
	}
	
	@Override
	public void run() {
		try {
			MessageManager.sendMessage("#get" + identifier, identifier);
			while(!Thread.currentThread().isInterrupted()) {
				if(in.available() > 0) {
					byte[] arr = new byte[in.available()];
					in.read(arr, 0, in.available());
					StringBuilder b = new StringBuilder("");
					for (int i = 0; i < arr.length; i++) {
						b.append((char) arr[i]);
					}
					String[] temp = b.toString().substring(1).split("#");
					for (int i = 0; i < temp.length; i++) {
						cmdMan.performCommand(identifier, temp[i].trim(), Thread.currentThread());
					}
				}
				Thread.sleep(50);
			}
		} catch (Exception e) {
		}
	}

}
