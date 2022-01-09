package server.commands;

import server.StateManager;
import server.commands.types.ServerCommand;
import server.util.MessageManager;

/**
 * During waiting --> retrieve the current state of each client
 */
public class GetCommand implements ServerCommand {

	@Override
	public void performCommand(String identifier, String data) throws Exception {
		MessageManager.sendMessage("#get" + StateManager.getData(identifier), identifier);
	}

}
