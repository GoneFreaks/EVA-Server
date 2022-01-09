package server.commands;

import db.QuestionDTO;
import server.StateManager;
import server.commands.types.ServerCommand;
import server.util.MessageManager;

/**
 * After switching from waiting to game --> get the first question
 */
public class GameCommand implements ServerCommand {

	@Override
	public void performCommand(String identifier, String data) throws Exception {
		QuestionDTO question = StateManager.getLobby(identifier).getQuestion(identifier);
		MessageManager.sendMessage("#gam" + question.toString(), identifier);
	}

}
