package de.tobiyas.util.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class CollectionMoreUtils {

	/**
	 * Private Random generator to use.
	 */
	private static final Random rand = new Random();
	
	
	/**
	 * Gets a Random element of the Collection.
	 * 
	 * @param coll Collection to search for
	 * @param rand to use
	 * 
	 * @return the random element.
	 */
	public static <E> E randomElement(Collection<? extends E> coll, Random rand) {
	    if (coll.size() == 0) {
	        return null; // or throw IAE, if you prefer
	    }

	    int index = rand.nextInt(coll.size());
	    if (coll instanceof List) { // optimization
	        return ((List<? extends E>) coll).get(index);
	    } else {
	        Iterator<? extends E> iter = coll.iterator();
	        for (int i = 0; i < index; i++) {
	            iter.next();
	        }
	        return iter.next();
	    }
	}
	
	/**
	 * Gets a Random element of the Collection.
	 * 
	 * @param coll Collection to search for
	 * 
	 * @return a random element
	 */ 
	public static <E> E randomElement(Collection<? extends E> coll) {
	   return randomElement(coll, rand);
	}
	
	
}
