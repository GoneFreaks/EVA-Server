package server.commands;

import server.StateManager;
import server.commands.types.ServerCommand;
import server.db.QuestionDTO;
import server.util.MessageManager;

public class GameCommand implements ServerCommand {

	@Override
	public void performCommand(String identifier, String data) throws Exception {
		QuestionDTO question = StateManager.getLobby(identifier).getQuestion(identifier);
		MessageManager.sendMessage("#gam" + question.toString(), identifier);
	}

}
