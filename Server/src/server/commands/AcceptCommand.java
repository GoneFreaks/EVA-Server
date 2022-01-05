package server.commands;

import server.DataManager;
import server.MessageManager;
import server.commands.types.ServerCommand;

public class AcceptCommand implements ServerCommand {

	@Override
	public void performCommand(String identifier, String data, Thread thread) throws Exception {
		DataManager.createLobby(identifier, data);
		DataManager.clearUserRequests(identifier, data);
		MessageManager.sendMessage("#acc", identifier);
		MessageManager.sendMessage("#acc", data);
	}

}
