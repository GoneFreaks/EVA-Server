package server.commands;

import server.StateManager;
import server.commands.types.ServerCommand;
import server.util.MessageManager;

public class AcceptCommand implements ServerCommand {

	@Override
	public void performCommand(String identifier, String data) throws Exception {
		if(StateManager.createLobby(identifier, data)) {
			StateManager.clearUserRequests(identifier, data);
			MessageManager.sendMessage("#acc", identifier);
			MessageManager.sendMessage("#acc", data);
		}
		else {
			MessageManager.sendMessage("#unl", identifier);
			MessageManager.sendMessage("#unl", data);
		}
	}

}
