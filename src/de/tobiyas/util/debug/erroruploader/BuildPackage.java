package de.tobiyas.util.debug.erroruploader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.bukkit.Bukkit;

import de.tobiyas.util.config.UtilConsts;

public class BuildPackage extends Thread{

	private String pluginVersion;
	private String pluginName;
	
	private String utilsVersion;
	private String utilsState;
	
	private Exception error;
	private ErrorUploader uploader;
	
	
	public BuildPackage(String pluginVersion, String pluginName, Exception error, ErrorUploader uploader){
		this.pluginVersion = pluginVersion;
		this.pluginName = pluginName;
		this.utilsVersion = UtilConsts.utilsBuildVersion;
		this.utilsState = UtilConsts.utilsBuildState;
		this.uploader = uploader;
		
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
			URL url = new URL("http://0x002a.de/upload.php");
			
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
			
	        uploader.writeback(response);
		} catch (Exception e){
			uploader.writeback("Error while uploading error: " + e.getClass().getName());
		}
	}
}
