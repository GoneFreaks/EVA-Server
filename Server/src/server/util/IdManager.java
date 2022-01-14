package server.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IdManager {
	
	private static int ID_SIZE = 3;
	private static List<String> identifier_list = new ArrayList<>();
	private static Random rand = new Random();
	
	public static synchronized String createIdentifier () {
			
		int counter = 0;
		String result;
		do {
			StringBuilder builder = new StringBuilder("@");
			for (int i = 0; i < ID_SIZE; i++) {
				builder.append(rand.nextInt(9));
			}
			result = builder.toString();

			if(counter++ > 8) {	
				ID_SIZE++;		// if it seems like no more ids are available, increase the length of the id and try to get a new one
				return createIdentifier();
			}
		} while (identifier_list.contains(result));
		identifier_list.add(result);
		return result;
		
	}
	
	public static synchronized void removeIdentifier(String id) {
		identifier_list.remove(id);
	}
	
}
