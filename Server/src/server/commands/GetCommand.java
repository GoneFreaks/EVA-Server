package server.commands;

import java.net.Socket;

import main.Output;
import server.DataManager;
import server.commands.types.ClientCommand;

public class GetCommand implements ClientCommand {

	@Override
	public String performCommand(Socket connection, String data, Thread thread) throws Exception {
		Output.print(this.getClass().getSimpleName());
		return DataManager.mapToString();
	}

}
