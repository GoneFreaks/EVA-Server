package server.commands.types;

public interface ServerCommand {

	public String performCommand(String identifier, String data, Thread thread) throws Exception;
	
}
