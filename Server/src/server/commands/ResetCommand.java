package server.commands;

import server.StateManager;
import server.commands.types.ServerCommand;

public class ResetCommand implements ServerCommand {

	@Override
	public void performCommand(String identifier, String data) throws Exception {
		StateManager.reset(identifier);
	}

}
