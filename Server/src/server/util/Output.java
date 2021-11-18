package server.util;

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
	}
	
}
