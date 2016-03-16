package de.tobiyas.util.file;

import java.io.Closeable;

public class IOUtils {

	
	/**
	 * Closes something quietly.
	 * @param closeable to close.
	 */
	public static void closeQuietly(Closeable closeable){
		if(closeable == null) return;
		try{ closeable.close(); }catch(Throwable exp){}
	}
	
	/**
	 * Closes something quietly.
	 * @param closeable to close.
	 */
	public static void closeQuietly(AutoCloseable closeable){
		if(closeable == null) return;
		try{ closeable.close(); }catch(Throwable exp){}
	}
	
}
