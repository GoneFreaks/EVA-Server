package server.commands;

import db.QuestionDTO;
import server.DataManager;
import server.commands.types.ServerCommand;
import server.util.MessageManager;

public class GameCommand implements ServerCommand {

	@Override
	public void performCommand(String identifier, String data) throws Exception {
		QuestionDTO question = DataManager.getLobby(identifier).getQuestion(identifier);
		MessageManager.sendMessage("#gam" + question.toString(), identifier);
	}

}
