package server.commands;

import java.net.Socket;

import main.Output;
import server.commands.types.ClientCommand;

public class DeleteCommand implements ClientCommand {

	@Override
	public String performCommand(Socket connection, String data, Thread thread) throws Exception {
		Output.print(this.getClass().getSimpleName());
		thread.interrupt();
		return "";
	}

}
