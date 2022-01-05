package server.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;

public class Output {

	private static final boolean DEBUG_OUTPUT = true;
	
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
				e.printStackTrace();
			}
		}
	}
	
}
