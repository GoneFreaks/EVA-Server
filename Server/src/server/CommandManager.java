package server;

import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

import main.Output;
import server.commands.DeleteCommand;
import server.commands.types.ClientCommand;

public class CommandManager {

	private ConcurrentHashMap<String, ClientCommand> storage;
	
	public CommandManager() {
		this.storage = new ConcurrentHashMap<>();
		this.storage.put("del", new DeleteCommand());
	}
	
	public void performCommand(Socket connection, String input) {
		String cmd = input.substring(0, 3);
		String data = input.substring(3);
		
		try {
			storage.get(cmd).performCommand(connection, data);
		} catch (Exception e) {
			Output.printException(e);
		}
		
	}
	
}
