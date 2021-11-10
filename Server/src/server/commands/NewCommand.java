package server.commands;

import java.net.Socket;

import main.Output;
import server.commands.types.ClientCommand;

public class NewCommand implements ClientCommand {

	// erzeuge eine neue Lobby
	@Override
	public void performCommand(Socket Connection, String data) throws Exception {
		Output.print(this.getClass().getSimpleName());
	}

}
