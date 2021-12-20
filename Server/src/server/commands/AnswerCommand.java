package server.commands;

import server.DataManager;
import server.Lobby;
import server.MessageManager;
import server.commands.types.ServerCommand;

public class AnswerCommand implements ServerCommand {

	@Override
	public void performCommand(String identifier, String data, Thread thread) throws Exception {
		int points;
		try {
			points = Integer.parseInt(data);
		} catch (Exception e) {
			points = 0;
		}
		Lobby lobby = DataManager.getLobby(identifier);
		lobby.addPoints(identifier, points);
		MessageManager.sendMessage(lobby.getQuestion().toString(), identifier);
	}

}
