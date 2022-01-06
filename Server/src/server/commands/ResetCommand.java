package server.commands;

import server.DataManager;
import server.commands.types.ServerCommand;

public class ResetCommand implements ServerCommand {

	@Override
	public void performCommand(String identifier, String data) throws Exception {
		DataManager.reset(identifier);
	}

}
