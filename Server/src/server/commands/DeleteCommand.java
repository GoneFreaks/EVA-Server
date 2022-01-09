package server.commands;

import server.MessageListener;
import server.StateManager;
import server.commands.types.ServerCommand;
import server.util.Identifier;
import server.util.MessageManager;

/**
 * Remove the client from every collection --> after shutdown of client
 */
public class DeleteCommand implements ServerCommand {

	@Override
	public void performCommand(String identifier, String data) throws Exception {
		StateManager.delete(identifier);
		MessageManager.removeId(identifier);
		MessageListener.INSTANCE.removeClient(identifier);
		Identifier.INSTANCE.removeIdentifier(identifier);
	}

}
