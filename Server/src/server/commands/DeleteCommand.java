package server.commands;

import server.DataManager;
import server.MessageManager;
import server.commands.types.ServerCommand;

public class DeleteCommand implements ServerCommand {

	@Override
	public void performCommand(String identifier, String data, Thread thread) throws Exception {
		DataManager.delete(identifier);
		MessageManager.removeId(identifier);
		thread.interrupt();
	}

}
