package server.util;

import java.io.File;
import java.io.PrintStream;
import java.net.InetAddress;

public class Filter {

	public static void filterOutputStreams() throws Exception {
		if(!InetAddress.getLocalHost().getHostAddress().startsWith("192.")) {
				File log_file = new File("log.txt");
				if(log_file.exists()) log_file.delete();
				log_file.createNewFile();
				System.setOut(new PrintStream(log_file));
				System.setErr(new PrintStream(log_file));
		}
	}
}
