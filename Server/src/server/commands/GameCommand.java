package server.commands;

import db.QuestionDTO;
import server.DataManager;
import server.MessageManager;
import server.commands.types.ServerCommand;

public class GameCommand implements ServerCommand {

	@Override
	public void performCommand(String identifier, String data, Thread thread) throws Exception {
		QuestionDTO question = DataManager.getLobby(identifier).getQuestion();
		MessageManager.sendMessage("gam" + question.toString(), identifier);
	}

}
