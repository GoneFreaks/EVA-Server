package server.commands;

import server.StateManager;
import server.commands.types.ServerCommand;
import server.db.QuestionDTO;
import server.util.Lobby;
import server.util.MessageManager;

public class AnswerCommand implements ServerCommand {

	@Override
	public void performCommand(String identifier, String data) throws Exception {
		int points;
		try {
			points = Integer.parseInt(data);
		} catch (Exception e) {
			points = 0;
		}
		Lobby lobby = StateManager.getLobby(identifier);
		lobby.addPoints(identifier, points);
		QuestionDTO question = lobby.getQuestion(identifier);
		if(question != null) MessageManager.sendMessage("#ans" + question.toString(), identifier);
	}

}
