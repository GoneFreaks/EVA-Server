package server.commands;

import server.DataManager;
import server.MessageManager;
import server.commands.types.ServerCommand;

public class GetCommand implements ServerCommand {

	@Override
	public void performCommand(String identifier, String data, Thread thread) throws Exception {
		MessageManager.sendMessage("#get" + DataManager.getData(identifier), identifier);
	}

}
