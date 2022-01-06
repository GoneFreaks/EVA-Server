package server.commands;

import server.DataManager;
import server.commands.types.ServerCommand;
import server.util.MessageManager;

public class AcceptCommand implements ServerCommand {

	@Override
	public void performCommand(String identifier, String data) throws Exception {
		DataManager.createLobby(identifier, data);
		DataManager.clearUserRequests(identifier, data);
		MessageManager.sendMessage("#acc", identifier);
		MessageManager.sendMessage("#acc", data);
	}

}
