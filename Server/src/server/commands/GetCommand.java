package server.commands;

import server.StateManager;
import server.commands.types.ServerCommand;
import server.util.MessageManager;

public class GetCommand implements ServerCommand {

	@Override
	public void performCommand(String identifier, String data) throws Exception {
		MessageManager.INSTANCE.sendMessage("#get" + StateManager.getData(identifier), identifier);
	}

}
