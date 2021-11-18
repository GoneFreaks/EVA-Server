package server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class MessageListener implements Runnable{

	private String identifier;
	
	private BufferedReader in;
	private PrintWriter out;
	private CommandManager cmdMan;
	
	public MessageListener(InputStream inStream, OutputStream outStream, CommandManager cmdMan, String identifier) {
		in = new BufferedReader(new InputStreamReader(inStream));
		out = new PrintWriter(outStream, true);
		this.cmdMan = cmdMan;
		this.identifier = identifier;
	}
	
	@Override
	public void run() {
		try {
			DataManager.addUser(identifier, out);
			out.println(identifier);
			while(!Thread.currentThread().isInterrupted()) {
				String line;
				if(in.ready() && (line = in.readLine()) != null) {
					String response = cmdMan.performCommand(identifier, line, Thread.currentThread());
					out.println(response);
				}
				Thread.sleep(100);
			}
		} catch (Exception e) {
		}
	}

}
