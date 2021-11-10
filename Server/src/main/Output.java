package main;

public class Output {

	private static final boolean DEBUG_OUTPUT = true;
	
	public static void print (String out) {
		if(DEBUG_OUTPUT) System.out.println(out);
	}
	
	public static void printException (Exception ex) {
		if(DEBUG_OUTPUT) ex.printStackTrace();
	}
	
}
