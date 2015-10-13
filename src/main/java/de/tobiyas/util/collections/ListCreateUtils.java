package de.tobiyas.util.collections;

import java.util.LinkedList;
import java.util.List;

public class ListCreateUtils {

	
	
	public static <T> List<T> single(T obj){
		List<T> single = new LinkedList<T>();
		single.add(obj);
		return single;
	}
	
	
	public static <T> List<T> multi(T... objs){
		List<T> single = new LinkedList<T>();
		for(T obj : objs){
			single.add(obj);
		}
		return single;
	}


	/**
	 * Does a To-Lowercase on all elements of a List.
	 * 
	 * @param list to use.
	 */
	public static void toLowerCase(List<String> list) {
		for(int i = 0; i < list.size(); i++){
			String toSet = list.get(i);
			if(toSet != null) list.set(i, toSet.toLowerCase());
		}
	}
	
	/**
	 * Does a To-Uppercase on all elements of a List.
	 * 
	 * @param list to use.
	 */
	public static void toUpperCase(List<String> list) {
		for(int i = 0; i < list.size(); i++){
			String toSet = list.get(i);
			if(toSet != null) list.set(i, toSet.toUpperCase());
		}
	}
	
}
