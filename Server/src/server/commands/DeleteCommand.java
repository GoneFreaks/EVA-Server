package server.commands;

import server.MessageListenerManager;
import server.StateManager;
import server.commands.types.ServerCommand;
import server.util.Identifier;
import server.util.MessageManager;

public class DeleteCommand implements ServerCommand {

	@Override
	public void performCommand(String identifier, String data) throws Exception {
		StateManager.delete(identifier);
		MessageManager.INSTANCE.removeId(identifier);
		MessageListenerManager.INSTANCE.removeClient(identifier);
		Identifier.INSTANCE.removeIdentifier(identifier);
	}

}
