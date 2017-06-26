package de.tobiyas.util.exception;

public class TryUtils {

	
	public static <T> T Try(Function<T> func, T defaultValue){
		try{ return func.doStuff(); }catch(Throwable exp){}
		
		return defaultValue;
	}
	
	
	
	public interface Function<T> {
		public T doStuff();
	}
	
}
