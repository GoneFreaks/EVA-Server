package server.commands;

import java.net.Socket;

import main.Output;
import server.commands.types.ClientCommand;

public class DeleteCommand implements ClientCommand {

	//kontrolliertes Verlassen während einem Spiel oder dem Warten auf ein Spiel
	@Override
	public String performCommand(Socket connection, String data) throws Exception {
		Output.print(this.getClass().getSimpleName());
		return "";
	}

}
