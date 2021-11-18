package server.commands.types;

public interface ServerCommand {

	public void performCommand(String identifier, String data, Thread thread) throws Exception;
	
}
