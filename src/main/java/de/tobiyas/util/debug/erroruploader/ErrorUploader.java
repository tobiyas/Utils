/*******************************************************************************
 * Copyright 2014 Tobias Welther
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.tobiyas.util.debug.erroruploader;

import java.util.logging.Logger;

import org.bukkit.plugin.Plugin;

public class ErrorUploader {

	private Plugin plugin;
	private Logger logger;
	
	public ErrorUploader(Plugin plugin, Logger errorLogger){
		this.plugin = plugin;
		this.logger = errorLogger;
	}
	
	public void uploadStacktrace(Throwable error){
		String pluginVersion = plugin.getDescription().getVersion();
		String pluginName = plugin.getName();
		
		BuildPackage buildPackage = new BuildPackage(pluginVersion, pluginName, error, logger);
		buildPackage.start();
	}
}
