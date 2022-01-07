package server.commands;

import server.DataManager;
import server.commands.types.ServerCommand;

/**
 * Ask another user for a game
 */
public class RequestCommand implements ServerCommand {

	@Override
	public void performCommand(String identifier, String data) throws Exception {
		DataManager.addRequest(identifier, data);
	}

}
