package server.commands;

import java.net.Socket;

import main.Output;
import server.commands.types.ClientCommand;

public class GetCommand implements ClientCommand {

	// Laden aller Spieler, welche momentan auf ein Spiel warten bzw. online sind
	@Override
	public void performCommand(Socket Connection, String data) throws Exception {
		Output.print(this.getClass().getSimpleName());
	}

}
