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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;

import de.tobiyas.util.config.UtilConsts;

public class BuildPackage extends Thread{

	//Actual adress to send error to
	private static final String sendAdress = "http://tobiyas.tk/upload.php";
	
	private String pluginVersion;
	private String pluginName;
	
	private String utilsVersion;
	private String utilsState;
	
	private Throwable error;
	private Logger logger;
	
	
	public BuildPackage(String pluginVersion, String pluginName, Throwable error, Logger logger){
		this.pluginVersion = pluginVersion;
		this.pluginName = pluginName;
		this.utilsVersion = UtilConsts.utilsBuildVersion;
		this.utilsState = UtilConsts.utilsBuildState;
		this.logger = logger;
		
		this.error = error;
	}
	
	private String encodePackage(){
		StringBuilder builder = new StringBuilder();
		try {
			builder.append(convertValue("serverName", Bukkit.getServer().getServerName())).append("&");
			builder.append(convertValue("bukkitBuild", Bukkit.getServer().getVersion())).append("&");
			builder.append(convertValue("pluginVersion", pluginVersion)).append("&");
			builder.append(convertValue("pluginName", pluginName)).append("&");
			builder.append(convertValue("utilsVersion", utilsVersion + " " + utilsState)).append("&");
			builder.append(convertValue("errorClass", error.getClass().getName())).append("&");
			builder.append(convertValue("errorMessage", error.getLocalizedMessage())).append("&");
			
			StackTraceElement[] trace = error.getStackTrace();
			String traceString = "";
			for(StackTraceElement element : trace)
				traceString += element.toString() + "\n";
			
			builder.append(convertValue("stacktrace", traceString));
		
		} catch (UnsupportedEncodingException e) {
		}
		
		return builder.toString();
	}
	
	private String convertValue(String key, String value) throws UnsupportedEncodingException{
		String temp = URLEncoder.encode(key, "UTF-8") + "="
		        + URLEncoder.encode(value, "UTF-8");
		return temp;
	}

	@Override
	public void run() {
		sendError();
		this.interrupt();
	}
	
	private void sendError(){
		try {
			URL url = new URL(sendAdress);
			
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);
			
			 // Write the data
	        final OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
	        writer.write(encodePackage());
	        writer.flush();

	        // Now read the response
	        final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        final String response = reader.readLine();

	        // close resources
	        writer.close();
	        reader.close();
			
	       	logger.log(Level.INFO, "Responce Code: " + response + " " + ErrorUploaderResponceCode.getMessageToError(response));
		} catch (Exception e){
			logger.log(Level.WARNING, "Error while uploading log: " + e.getLocalizedMessage().toString());
			e.printStackTrace();
		}
	}
}
