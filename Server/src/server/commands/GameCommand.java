package server.commands;

import db.QuestionDTO;
import server.StateManager;
import server.commands.types.ServerCommand;
import server.util.MessageManager;

public class GameCommand implements ServerCommand {

	@Override
	public void performCommand(String identifier, String data) throws Exception {
		QuestionDTO question = StateManager.getLobby(identifier).getQuestion(identifier);
		MessageManager.INSTANCE.sendMessage("#gam" + question.toString(), identifier);
	}

}
