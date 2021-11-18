package server.commands;

import server.DataManager;
import server.commands.types.ServerCommand;
import server.util.Output;

public class GetCommand implements ServerCommand {

	@Override
	public String performCommand(String identifier, String data, Thread thread) throws Exception {
		Output.print("\t" + this.getClass().getSimpleName());
		return DataManager.getData(identifier);
	}

}
