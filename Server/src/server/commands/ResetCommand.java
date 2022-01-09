package server.commands;

import server.StateManager;
import server.commands.types.ServerCommand;

/**
 * After a lobby/game has finished
 */
public class ResetCommand implements ServerCommand {

	@Override
	public void performCommand(String identifier, String data) throws Exception {
		StateManager.reset(identifier);
	}

}
