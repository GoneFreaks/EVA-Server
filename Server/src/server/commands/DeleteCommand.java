package server.commands;

import server.DataManager;
import server.Listener;
import server.MessageManager;
import server.commands.types.ServerCommand;

public class DeleteCommand implements ServerCommand {

	@Override
	public void performCommand(String identifier, String data) throws Exception {
		DataManager.delete(identifier);
		MessageManager.removeId(identifier);
		Listener.INSTANCE.removeClient(identifier);
	}

}
