package server.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Identifier {
	
	public static int ID_SIZE = 3;
	
	private List<String> already = new ArrayList<>();
	
	private Random rand;
	
	public Identifier () {
		rand = new Random();
	}
	
	public synchronized String createIdentifier () {
			
		int counter = 0;
		String result;
		do {
			StringBuilder builder = new StringBuilder("#");
			for (int i = 0; i < ID_SIZE; i++) {
				builder.append(rand.nextInt(9));
			}
			result = builder.toString();

			if(counter++ > 8) {	// if it seems like no more ids are available
				ID_SIZE++;
				return createIdentifier();
			}
		} while (already.contains(result));
		already.add(result);
		return result;
		
	}
	
}
