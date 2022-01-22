package server.commands;

import server.StateManager;
import server.commands.types.ServerCommand;
import server.listener.MessageListenerManager;
import server.util.IdManager;
import server.util.MessageManager;

public class DeleteCommand implements ServerCommand {

	@Override
	public void performCommand(String identifier, String data) throws Exception {
		StateManager.delete(identifier, true);
		MessageManager.removeUser(identifier);
		MessageListenerManager.removeClient(identifier);
		IdManager.removeIdentifier(identifier);
	}

}
