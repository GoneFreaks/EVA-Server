package server.commands;

import java.net.Socket;
import java.util.Random;

import main.Output;
import server.DataManager;
import server.commands.types.ClientCommand;

public class LoginCommand implements ClientCommand {

	// Login eines Sockets --> Antwort ist ein 4-stelliger Identifier
	
	private Random rand;
	
	public LoginCommand() {
		this.rand = new Random();
	}
	
	@Override
	public void performCommand(Socket connection, String data) throws Exception {
		Output.print(this.getClass().getSimpleName());
		StringBuilder builder = new StringBuilder("#");
		for (int i = 0; i < 4; i++) {
			builder.append(rand.nextInt(9));
		}
		DataManager.addSocket(connection, data + builder.toString());
	}

}
