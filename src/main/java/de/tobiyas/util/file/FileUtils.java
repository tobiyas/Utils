package de.tobiyas.util.file;

import java.io.File;
import java.io.FileFilter;
import java.util.HashSet;
import java.util.Set;

public class FileUtils {

	
	/**
	 * Returns all Files passed.
	 * 
	 * @param base to start.
	 * @param filter to filter to.
	 * 
	 * @return the Set of files.
	 */
	public static Set<File> getAllFiles(File base, FileFilter filter){
		Set<File> files = new HashSet<File>();
		if(base.isFile()) {
			if(filter == null || filter.accept(base)) files.add(base);
			return files;
		}
		
		for(File file : base.listFiles()){
			files.addAll(getAllFiles(file, filter));
		}
		
		return files;
	}
	

	/**
	 * Returns all Files passed.
	 * 
	 * @param base to start.
	 * 
	 * @return the Set of files.
	 */
	public static Set<File> getAllFiles(File base){
		return getAllFiles(base, null);
	}
	
}
