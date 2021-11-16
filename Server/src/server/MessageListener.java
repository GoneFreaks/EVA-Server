package server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import main.Output;

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
			String line;
			out.println(identifier);
			while((line = in.readLine()) != null) {
				String response = cmdMan.performCommand(null, line);
				out.println(response);
			}
		} catch (Exception e) {
			Output.printException(e);
		}
	}

}
