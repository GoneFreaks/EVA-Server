package server.commands;

import server.commands.types.ServerCommand;
import server.util.Output;

public class DeleteCommand implements ServerCommand {

	@Override
	public String performCommand(String identifier, String data, Thread thread) throws Exception {
		Output.print("\t" + this.getClass().getSimpleName());
		thread.interrupt();
		return "";
	}

}
