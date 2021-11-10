package server.commands;

import java.net.Socket;

import server.commands.types.ClientCommand;

public class DeleteCommand implements ClientCommand {

	@Override
	public void performCommand(Socket connection, String data) throws Exception {
		System.out.println(data);
	}

}
