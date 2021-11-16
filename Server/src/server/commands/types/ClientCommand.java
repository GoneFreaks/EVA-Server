package server.commands.types;

import java.net.Socket;

public interface ClientCommand {

	public String performCommand(Socket connection, String data, Thread thread) throws Exception;
	
}
