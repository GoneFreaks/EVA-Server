package server.commands;

import server.commands.types.ServerCommand;

public class DeleteCommand implements ServerCommand {

	@Override
	public void performCommand(String identifier, String data, Thread thread) throws Exception {
		thread.interrupt();
	}

}
