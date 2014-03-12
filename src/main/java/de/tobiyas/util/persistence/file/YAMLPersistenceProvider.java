package de.tobiyas.util.persistence.file;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.tobiyas.util.config.YAMLConfigExtended;

public class YAMLPersistenceProvider {
	
	/**
	 * The Cached Player YAML files
	 */
	protected static Map<String, YAMLConfigExtended> playerYamls = new HashMap<String, YAMLConfigExtended>();
	
	
	/**
	 * The YAMLs for the Channels
	 */
	protected YAMLConfigExtended channelsYaml;
	
	/**
	 * The knownPlayers.
	 */
	protected Set<String> knownPlayers;

	/**
	 * The file to the root of the data.
	 */
	protected final File root;
	
	/**
	 * The Plugin to use
	 */
	protected final JavaPlugin plugin;
	
	/**
	 * Creates a new Provider
	 * 
	 * @param plugin to init to
	 */
	public YAMLPersistenceProvider(JavaPlugin plugin) {
		this.plugin = plugin;
		this.root = new File(plugin.getDataFolder(), "PlayerData");
		
		if(!root.exists()){
			root.mkdirs();
		}
	}
	

	/**
	 * Returns the already loaded Player YAML File.
	 * This is a lazy load.
	 * 
	 * @param playerName to load
	 * 
	 * @return the loaded file. NEVER!!! SAVE IT!!! This will be done Async to NOT stop the Bukkit thread!
	 */
	public YAMLConfigExtended getLoadedPlayerFile(String playerName) {
		if(knownPlayers == null){
			rescanKnownPlayers();
		}
		
		if(playerYamls.containsKey(playerName)){
			YAMLConfigExtended playerConfig = playerYamls.get(playerName);
			if(playerConfig.getValidLoad()){
				cacheHit++;
				return playerConfig;
			}
			
			cacheMiss++;
			return playerConfig.load();
		}
		
		if(knownPlayers.contains(playerName)){
			YAMLConfigExtended playerConfig = new YAMLConfigExtended(new File(root, playerName + ".yml")).load();
			playerYamls.put(playerName, playerConfig);
			
			cacheMiss++;
			return playerConfig;
		}
		
		//Whether in cache, nor known.
		YAMLConfigExtended playerConfig = new YAMLConfigExtended(new File(root, playerName + ".yml")).load();
		playerYamls.put(playerName, playerConfig);
		
		rescanKnownPlayers();
		cacheMiss++;
		return playerConfig;
	}

	/**
	 * Returns a List of all Players known.
	 * 
	 * @return the known Players.
	 */
	public Set<String> getAllPlayersKnown(){
		if(knownPlayers == null){
			rescanKnownPlayers();
		}
		
		return knownPlayers;
	}
	
	/**
	 * Rescans the Known Players.
	 */
	protected void rescanKnownPlayers(){
		FilenameFilter filter = new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				if(name.startsWith("playerData")){
					return false;
				}
				
				return name.endsWith(".yml");
			}
		};
		
		if(knownPlayers == null){
			knownPlayers = new HashSet<String>();
		}
		
		knownPlayers.clear();
		String[] playerFileNames = root.list(filter);
		if(playerFileNames == null) return;
		
		for(String playerFile : playerFileNames){
			knownPlayers.add(playerFile.replace(".yml", ""));
		}
	}
	
	/**
	 * Starts an Async saving of the files.
	 */
	@SuppressWarnings("deprecation")
	public void startAsyncSaving(){
		Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
			
			@Override
			public void run() {
				for(YAMLConfigExtended config : playerYamls.values()){
					
					//Start saving if needed.
					if(config.isDirty()){
						config.saveAsync();
					}
				}
			}
		}, 1);
	}
	
	
	/**
	 * Saves all dirts configs synchronously.
	 */
	public void startSyncSaving(){
		for(YAMLConfigExtended config : playerYamls.values()){
			if(config.isDirty()){
				config.save();
			}
		}
	}
	
	
	private static int cacheHit = 0;
	private static int cacheMiss = 0;
	
	/**
	 * Returns the Hitrate of the YML loading
	 * 
	 * @return hitrate.
	 */
	public static double getCacheHitRate(){
		double total = cacheHit + cacheMiss;
		return total / cacheHit;
	}
	
	
	/**
	 * Returns the total number of accesses.
	 * 
	 * @return total number of accesses.
	 */
	public static int getTotalTries(){
		return cacheHit + cacheMiss;
	}
	
	
	/**
	 * The Provider to init with.
	 */
	private static YAMLPersistenceProvider provider;
	
	
	/**
	 * Inits the provider.
	 * 
	 * @param plugin to init with
	 */
	public static void init(JavaPlugin plugin){
		provider = new YAMLPersistenceProvider(plugin);
	}
	
	/**
	 * Returns the PersistanceProvider.
	 * <br>Will throw an NullPointer Exception IF not inited!
	 * 
	 * @return persistance Provider.
	 */
	public static YAMLPersistenceProvider getProvider(){
		if(provider == null) throw new NullPointerException("The YAMLPersistance Provider has NOT been inited!");
		return provider;
	}
}
