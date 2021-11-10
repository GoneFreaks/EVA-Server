package server;

import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

import main.Output;
import server.commands.AnswerCommand;
import server.commands.DeleteCommand;
import server.commands.GetCommand;
import server.commands.NewCommand;
import server.commands.types.ClientCommand;

public class CommandManager {

	private ConcurrentHashMap<String, ClientCommand> storage;
	
	public CommandManager() {
		this.storage = new ConcurrentHashMap<>();
		this.storage.put("del", new DeleteCommand());
		this.storage.put("get", new GetCommand());
		this.storage.put("new", new NewCommand());
		this.storage.put("ans", new AnswerCommand());
	}
	
	public void performCommand(Socket connection, String input) {
		String cmd = input.substring(0, 3);
		String data = input.substring(3);
		
		if(storage.get(cmd) != null) {
			try {
				storage.get(cmd).performCommand(connection, data);
			} catch (Exception e) {
				Output.printException(e);
			}
		}
		else Output.print("UNBEKANNTER COMMAND: " + cmd);
	}
}
