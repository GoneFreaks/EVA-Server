package server.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Identifier {
	
	public static final int ID_SIZE = 4;
	
	private List<String> already = new ArrayList<>();
	
	private Random rand;
	private Object object;	// private Lock-Object --> Hijack
	
	public Identifier () {
		rand = new Random();
		object = new Object();
	}
	
	public String createIdentifier () {
		synchronized (object) {
			
			int counter = 0; // if all ids are used --> don't block
			String result;
			do {
				StringBuilder builder = new StringBuilder("#");
				for (int i = 0; i < ID_SIZE; i++) {
					builder.append(rand.nextInt(9));
				}
				result = builder.toString();

				if(counter++ > 8) return null;
			} while (already.contains(result));
			already.add(result);
			return result;
		}
	}
	
}
