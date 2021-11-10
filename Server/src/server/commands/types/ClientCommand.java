package server.commands.types;

import java.net.Socket;

public interface ClientCommand {

	public void performCommand(Socket Connection, String data) throws Exception;
	
}
