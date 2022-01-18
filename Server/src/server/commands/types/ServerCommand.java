package server.commands.types;

public interface ServerCommand {

	public void performCommand(String identifier, String data) throws Exception;
	
}
