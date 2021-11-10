package server.commands;

import java.net.Socket;

import main.Output;
import server.DataManager;
import server.commands.types.ClientCommand;

public class GetCommand implements ClientCommand {

	// Antwort ist eine Liste aller eingeloggten Sockets und deren Status
	@Override
	public void performCommand(Socket connection, String data) throws Exception {
		Output.print(this.getClass().getSimpleName());
		Output.print(DataManager.mapToString());
	}

}
