package server.commands;

import server.DataManager;
import server.Listener;
import server.commands.types.ServerCommand;
import server.util.MessageManager;

/**
 * Remove the client from every collection --> after shutdown of client
 */
public class DeleteCommand implements ServerCommand {

	@Override
	public void performCommand(String identifier, String data) throws Exception {
		DataManager.delete(identifier);
		MessageManager.removeId(identifier);
		Listener.INSTANCE.removeClient(identifier);
	}

}
