package server.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;

/**
 * Every output is redirected into this class</br>
 * Depending on the current HostAddress debug-outputs will be used</br>
 * If the flag is false no System.out will be used, meanwhile Exceptions are written into a file
 */
public class Output {

	private static boolean DEBUG_OUTPUT = false;
	
	public static void checkOutput() {
		try {
			if(InetAddress.getLocalHost().getHostAddress().startsWith("192.")) DEBUG_OUTPUT = true;
		} catch (Exception e) {
		}
	}
	
	/**
	 * Only print if DEBUG_OUTPUT flag is true
	 */
	public static void print (String out) {
		if(DEBUG_OUTPUT) System.out.println(out);
	}
	
	/**
	 * Only print if DEBUG_OUTPUT flag is true
	 */
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
