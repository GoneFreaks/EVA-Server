package server.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;

public class Output {

	private static boolean DEBUG_OUTPUT = false;
	
	public static void checkOutput() {
		try {
			if(InetAddress.getLocalHost().getHostAddress().startsWith("192.")) DEBUG_OUTPUT = true;
		} catch (Exception e) {
		}
	}
	
	public static void println (String out) {
		if(DEBUG_OUTPUT) System.out.println(out);
	}
	
	public static void print (String out) {
		if(DEBUG_OUTPUT) System.out.print(out);
	}
	
	public static void printException (Exception ex) {
		if(DEBUG_OUTPUT) ex.printStackTrace();
		else {
			try (BufferedWriter writer = new BufferedWriter(new FileWriter("log.txt", true))) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				ex.printStackTrace(pw);
				writer.write(sw.toString());
			} catch (Exception e) {
			}
		}
	}
	
}
