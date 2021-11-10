package server.commands.types;

import java.net.Socket;

public interface ClientCommand {

	public void performCommand(Socket connection, String data) throws Exception;
	
}
