package server;

import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

import main.Output;
import server.commands.DeleteCommand;
import server.commands.GetCommand;
import server.commands.types.ClientCommand;

public class CommandManager {

	private ConcurrentHashMap<String, ClientCommand> storage;
	
	public CommandManager() {
		this.storage = new ConcurrentHashMap<>();
		this.storage.put("del", new DeleteCommand());
		this.storage.put("get", new GetCommand());
	}
	
	public String performCommand(Socket connection, String input, Thread thread) {
		String cmd = input.substring(0, 3);
		String data = input.substring(3);
		
		if(storage.get(cmd) != null) {
			try {
				return storage.get(cmd).performCommand(connection, data, thread);
			} catch (Exception e) {
				Output.printException(e);
			}
		}
		else Output.print("UNBEKANNTER COMMAND: " + cmd);
		return "";
	}
}
