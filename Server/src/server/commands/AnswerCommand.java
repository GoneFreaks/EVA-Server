package server.commands;

import java.net.Socket;

import main.Output;
import server.commands.types.ClientCommand;

public class AnswerCommand implements ClientCommand {

	// Ein Socket hat eine Antwort geschickt
	@Override
	public void performCommand(Socket connection, String data) throws Exception {
		Output.print(this.getClass().getSimpleName());
	}

}
