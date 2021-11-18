package server;

import java.util.concurrent.ConcurrentHashMap;

import server.commands.DeleteCommand;
import server.commands.GetCommand;
import server.commands.RequestCommand;
import server.commands.types.ServerCommand;
import server.util.Output;

public class CommandManager {

	private ConcurrentHashMap<String, ServerCommand> storage;
	
	public CommandManager() {
		this.storage = new ConcurrentHashMap<>();
		this.storage.put("del", new DeleteCommand());
		this.storage.put("get", new GetCommand());
		this.storage.put("req", new RequestCommand());
	}
	
	public String performCommand(String identifier, String input, Thread thread) {
		String cmd = input.substring(0, 3);
		String data = input.substring(3);
		
		if(storage.get(cmd) != null) {
			try {
				return cmd + storage.get(cmd).performCommand(identifier, data, thread);
			} catch (Exception e) {
				Output.printException(e);
			}
		}
		else Output.print("UNBEKANNTER COMMAND: " + cmd);
		return "";
	}
}
