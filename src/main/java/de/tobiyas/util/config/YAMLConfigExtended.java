package de.tobiyas.util.config;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import de.tobiyas.util.config.returncontainer.DropContainer;


public class YAMLConfigExtended extends YamlConfiguration {

	/**
	 * The path to the Folder of the Config
	 */
	private String savePath;
	
	/**
	 * The Total path of the Config
	 */
	private String totalPath;
	
	/**
	 * Tells if the loading was valid or it did not work
	 */
	private boolean validLoad;
	
	/**
	 * Creates a YAML config from a File
	 * 
	 * @param file where the config is located
	 */
	public YAMLConfigExtended(File file){
		super();
		validLoad = false;
		this.savePath = file.getParent();
		
		this.totalPath = file.getPath();
	}
	
	/**
	 * Creates a YAML config from a path + a file name
	 * 
	 * @param savePath the parent directory of the Config.
	 * @param fileName the file name of the Config.
	 */
	public YAMLConfigExtended(String savePath, String fileName){
		super();
		validLoad = false;
		this.savePath = savePath;
		
		this.totalPath = savePath + File.separator + fileName;
	}
	
	/**
	 * Creates a YAML Config from the path
	 * 
	 * @param savePath the path where the Config is located
	 */
	public YAMLConfigExtended(String savePath){
		super();
		validLoad = false;
		
		File tempFile = new File(savePath);
		this.savePath = tempFile.getParent();
		
		this.totalPath = savePath;
	}
	
	
	/**
	 * Util for YAMLReader to get all child-keys as Set<String> for a given Node 
	 * 
	 * @param yamlNode the Node to get the children from
	 * @return the children as Set<String>
	 */
	public Set<String> getChildren(String yamlNode){
		try{
			ConfigurationSection tempMem = getConfigurationSection(yamlNode);
			Set<String> tempSet = tempMem.getKeys(false);
			return tempSet;
			
		}catch(Exception e){
			Set<String> empty = new LinkedHashSet<String>();
			return empty;
		}
	}
	
	
	/**
	 * Util for YAMLReader to get all child-keys as Set<String> for the root
	 * 
	 * @return the children as Set<String>
	 */
	public Set<String> getRootChildren(){
		try{
			ConfigurationSection tempMem = getRoot();
			Set<String> tempSet = tempMem.getKeys(false);
			return tempSet;
			
		}catch(Exception e){
			Set<String> empty = new LinkedHashSet<String>();
			return empty;
		}
	}
	
	/**
	 * Saves the Config to the File passed in construction.
	 * 
	 * @return
	 */
	public boolean save(){
		File file = fileCheck();
		try {
			this.save(file);
		} catch (IOException e) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Loads the Config.
	 * If not existent, the file is created.
	 * {@link #getValidLoad()} tells if it worked.
	 * 
	 * @return the Object loaded
	 */
	public YAMLConfigExtended load(){
		File savePathFile = new File(totalPath);
		if(!savePathFile.exists()){
			try {
				savePathFile.createNewFile();
			} catch (IOException e) {}
		}
			
		File saveFile = fileCheck();
		if(saveFile == null) {
			System.out.println("saveFile == null: " + savePath);
			validLoad = false;
			return this;
		}
				
		try {
			load(saveFile);
		} catch (Exception e) {
			validLoad = false;
			System.out.println("Error on loading YamlConfig: " + savePath);
			return this;
		}
		
		validLoad = true;
		return this;
	}
	
	
	/**
	 * Tells if the Config is loaded correct.
	 * If true everything is fine.
	 * If false, loading did not work or it wasn't loaded.
	 * 
	 * @return true if loading worked.
	 */
	public boolean getValidLoad(){
		return validLoad;
	}
		
	
	private File fileCheck(){		
		File fileFile = new File(totalPath);
		if(!fileFile.exists()){
			File saveDir = new File(savePath);
			if(!saveDir.exists())
				saveDir.mkdirs();
				
			try {
				fileFile.createNewFile();
			} catch (IOException e) {
				//plugin.log("Error creating new File :" + file);
				return null;
			}
		}
		return fileFile;
	}
	
	@Override
	public void set(String path, Object object){
		if(object instanceof Location){
			setLocation(path, (Location) object);
			return;
		}
		
		if(object instanceof DropContainer){
			setDropContainer(path, (DropContainer) object);
			return;
		}
		
		super.set(path, object);
	}
	
	/**
	 * Saves a Location to the given Path.
	 * 
	 * @param path 
	 * @param location
	 */
	public void setLocation(String path, Location location){
		double locX = location.getX();
		double locY = location.getY();
		double locZ = location.getZ();
		
		double pitch = location.getPitch();
		double yaw = location.getYaw();
		
		String world = location.getWorld().getName();
		
		createSection(path);
		set(path + ".x", locX);
		set(path + ".y", locY);
		set(path + ".z", locZ);
		
		set(path + ".pitch", pitch);
		set(path + ".yaw", yaw);

		set(path + ".world", world);
	}
	
	public Location getLocation(String path, Location defaultLocation){
		double locX = getDouble(path + ".x");
		double locY = getDouble(path + ".y");
		double locZ = getDouble(path + ".z");
		
		float yaw = (float) getDouble(path + ".yaw");
		float pitch = (float) getDouble(path + ".pitch");
		
		
		String world = getString(path + ".world");
		if(world == null) return defaultLocation;
		
		World World = Bukkit.getWorld(world);
		if(World == null) return defaultLocation;
		
		Location loc = new Location(World, locX, locY, locZ, yaw, pitch);
		return loc;
	}
	
	public Location getLocation(String path){
		return getLocation(path, null);
	}
	
	
	public void setDropContainer(String path, int itemID, int damageValue, int minAmount, int maxAmount, double probability){
		DropContainer container = new DropContainer(itemID, minAmount, maxAmount, probability);
		setDropContainer(path, container);
	}
	
	public void setDropContainer(String path, DropContainer container){
		String pre = path;		
		createSection(pre);
		set(pre + ".item", container.getItem());
		set(pre + ".min", container.getMin());
		set(pre + ".max", container.getMax());
		set(pre + ".probability", container.getProbability());
	}
	
	public DropContainer getDropContainer(String path){		
		ItemStack item = getItemStack(path + ".item", new ItemStack(0));
		int minAmount = getInt(path + ".min", 0);
		int maxAmount = getInt(path + ".max", 0);
		double probability = getDouble(path + ".probability", 0);

		return new DropContainer(item, minAmount, maxAmount, probability);
	}
	
	
	public File getFileLoadFrom(){
		return new File(totalPath);
	}

}

