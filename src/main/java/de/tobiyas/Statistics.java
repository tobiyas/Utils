package de.tobiyas;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.plugin.Plugin;

public class Statistics {

	/**
	 * The Map of plugin -> Log.
	 */
	private static final Map<Plugin, LogClass> logmap = new HashMap<Plugin, Statistics.LogClass>();
	
	/**
	 * The Start time.
	 */
	private static long startTime = System.currentTimeMillis();
	
	
	/**
	 * Logs the Call.
	 * 
	 * @param plugin to use
	 * @param name to use 
	 * @param calls to use
	 * @param timeMS to use
	 * @param timeNano to use
	 */
	public static void log(Plugin plugin, boolean sync, String name, int calls, long timeMS, long timeNano){
		LogClass log = logmap.get(plugin);
		if(log == null){
			log = new LogClass();
			logmap.put(plugin, log);
		}
		
		log.add(name, sync, calls, timeMS, timeNano);		
	}
	
	
	/**
	 * Logs the Call.
	 * 
	 * @param plugin to use
	 * @param name to use 
	 * @param timeMS to use
	 * @param timeNano to use
	 */
	public static void log(Plugin plugin, boolean sync, String name, long timeMS, long timeNano){
		log(plugin, sync, name, 1, timeMS, timeNano);
	}
	
	
	/**
	 * Returns the Log for the Plugin or null.
	 * 
	 * @param plugin to search.
	 * @return the log or null.
	 */
	public static LogClass getLog(Plugin plugin){
		return logmap.get(plugin);
	}
	
	
	/**
	 * Resets the statistic for this plugin.
	 * 
	 * @param plugin to reset.
	 */
	public static void reset(Plugin plugin){
		logmap.remove(plugin);
	}
	
	
	/**
	 * Resets all Statistics
	 */
	public static void resetAll(){
		logmap.clear();
		startTime = System.currentTimeMillis();
	}
	
	
	public static long lastRestart(){
		return startTime;
	}
	
	
	
	public static class LogClass{
		
		/**
		 * The Map of name -> Entry.
		 */
		private final Map<String,LogEntry> syncEntryMap = new HashMap<String, Statistics.LogEntry>();
		
		/**
		 * The Map of name -> Entry.
		 */
		private final Map<String,LogEntry> asyncEntryMap = new HashMap<String, Statistics.LogEntry>();
		
		
		public void add(String name, boolean sync, int calls, long timeMsNeeded, long timeNanoNeeded){
			LogEntry entry = sync ? syncEntryMap.get(name) : asyncEntryMap.get(name);
			if(entry == null){
				entry = new LogEntry();
				if(sync) syncEntryMap.put(name, entry);
				else asyncEntryMap.put(name, entry);
			}
			
			entry.add(calls, timeMsNeeded, timeNanoNeeded);
		}
		
		
		public Collection<String> presentSync(){
			return syncEntryMap.keySet();
		}
		
		public Collection<String> presentAsync(){
			return asyncEntryMap.keySet();
		}
		
		
		public LogEntry getSyncEntry(String name){
			return syncEntryMap.get(name);
		}
		
		public LogEntry getAsyncEntry(String name){
			return asyncEntryMap.get(name);
		}
		
		
		public long getTotalSyncTimeMS(){
			long time = 0;
			for(LogEntry entry : syncEntryMap.values()){
				time += entry.getTimeMsNeeded();
			}
			
			return time;
		}
		
		
		public long getTotalAsyncTimeMS(){
			long time = 0;
			for(LogEntry entry : asyncEntryMap.values()){
				time += entry.getTimeMsNeeded();
			}
			
			return time;
		}

		
		public long getTotalSyncTimeNano(){
			long time = 0;
			for(LogEntry entry : syncEntryMap.values()){
				time += entry.getTimeNanoNeeded();
			}
			
			return time;
		}

		
		public long getTotalAsyncTimeNano(){
			long time = 0;
			for(LogEntry entry : asyncEntryMap.values()){
				time += entry.getTimeNanoNeeded();
			}
			
			return time;
		}
		
	}
	
	
	
	public static class LogEntry{
		
		private int calls = 0;
		private long timeMsNeeded = 0;
		private long timeNanoNeeded = 0;
		
		
		public void add(int calls, long timeMsNeeded, long timeNanoNeeded){
			this.calls += calls;
			this.timeMsNeeded += timeMsNeeded;
			this.timeNanoNeeded += timeNanoNeeded;
		}


		public int getCalls() {
			return calls;
		}

		public long getTimeMsNeeded() {
			return timeMsNeeded;
		}

		public long getTimeNanoNeeded() {
			return timeNanoNeeded;
		}
		
	}
	
	
}
