package server.commands;

import server.DataManager;
import server.commands.types.ServerCommand;
import server.util.Output;

public class RequestCommand implements ServerCommand {

	@Override
	public String performCommand(String identifier, String data, Thread thread) throws Exception {
		Output.print("\t" + this.getClass().getSimpleName());
		DataManager.addRequest(identifier, data);
		return "";
	}

}
