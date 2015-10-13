package de.tobiyas.util.config;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

public class YMLConfigFilter implements FilenameFilter, FileFilter {

	@Override
	public boolean accept(File dir, String name) {
		return name.endsWith(".yml");
	}

	@Override
	public boolean accept(File pathname) {
		return pathname.getName().endsWith(".yml");
	}

}
