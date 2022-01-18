package server.util;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;

public class Filter {

	public static void filter() {
		try {
			if(!InetAddress.getLocalHost().getHostAddress().startsWith("192.")) {
				System.setOut(new Interceptor(System.out));
				File log_file = new File("log.txt");
				if(log_file.exists()) log_file.delete();
				log_file.createNewFile();
				System.setErr(new PrintStream(log_file));
			}
		} catch (Exception e) {
		}
	}
}

class Interceptor extends PrintStream {
	public Interceptor(OutputStream out) {super(out, true);}
	@Override
	public void print(String s) {}
	@Override
	public void println(String s) {}
}
