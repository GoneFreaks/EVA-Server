package server;

import java.util.concurrent.ConcurrentHashMap;

import server.commands.AcceptCommand;
import server.commands.AnswerCommand;
import server.commands.DeleteCommand;
import server.commands.GameCommand;
import server.commands.GetCommand;
import server.commands.RequestCommand;
import server.commands.ResetCommand;
import server.commands.types.ServerCommand;

public class CommandManager {

	private ConcurrentHashMap<String, ServerCommand> storage;
	
	public CommandManager() {
		this.storage = new ConcurrentHashMap<>();
		this.storage.put("del", new DeleteCommand());
		this.storage.put("get", new GetCommand());
		this.storage.put("req", new RequestCommand());
		this.storage.put("acc", new AcceptCommand());
		this.storage.put("gam", new GameCommand());
		this.storage.put("ans", new AnswerCommand());
		this.storage.put("new", new ResetCommand());
	}
	
	public void performCommand(String identifier, String input) {
		String cmd = input.substring(0, 3);
		String data = input.substring(3);
		
		if(storage.get(cmd) != null) {
			try {
				if(!cmd.equals("get")) System.out.println("\t" + identifier + ": " + storage.get(cmd).getClass().getSimpleName());
				storage.get(cmd).performCommand(identifier, data);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else System.out.println("UNBEKANNTER COMMAND: " + cmd);
	}
}
