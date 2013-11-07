package de.tobiyas.util.config;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
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
		
		if(object instanceof Material){
			setMaterial(path, (Material) object);
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
		if(location == null) return;
		
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
		createSection(path);
		set(path + ".item", container.getItem());
		set(path + ".min", container.getMin());
		set(path + ".max", container.getMax());
		set(path + ".probability", container.getProbability());
	}
	
	public DropContainer getDropContainer(String path){		
		ItemStack item = getItemStack(path + ".item", new ItemStack(Material.AIR));
		int minAmount = getInt(path + ".min", 0);
		int maxAmount = getInt(path + ".max", 0);
		double probability = getDouble(path + ".probability", 0);

		return new DropContainer(item, minAmount, maxAmount, probability);
	}
	
	
	/**
	 * Sets the Material to a path
	 * 
	 * @param path
	 * @param material
	 */
	public void setMaterial(String path, Material material){
		set(path, material.name());
	}
	
	/**
	 * Gets a Material at the Path. Returns the Default Material if not found or not loadable.
	 * 
	 * @param path to search
	 * @param defaultMaterial to pass when failed.
	 * 
	 * @return the loaded material or the DefaultMaterial.
	 */
	public Material getMaterial(String path, Material defaultMaterial){
		Material mat = Material.valueOf(getString(path).toUpperCase());
		return mat == null ? defaultMaterial : mat;
	}
	

	/**
	 * ItemStack To save
	 * 
	 * @param pre to save to
	 * @param item to save
	 */
	public void setItemStack(String pre, ItemStack item){
		createSection(pre + ".data");
		
		for(Entry<String, Object> entry : item.serialize().entrySet()){
			set(pre + ".data." + entry.getKey(), entry.getValue());			
		}
	}
	
	/**
	 * ItemStack To save
	 * 
	 * @param pre to save to
	 * @param item to save
	 */
	public ItemStack getItemStack(String pre, ItemStack defaultStack){
		try{
			Map<String, Object> serialized = new HashMap<String, Object>();
			for(String data : getChildren(pre + ".data")){
				Object obj = get(pre + ".data." + data);
				serialized.put(data, obj);
			}
			
			return ItemStack.deserialize(serialized);
		}catch(Exception exp){
			return defaultStack;
		}
	}
	
	public File getFileLoadFrom(){
		return new File(totalPath);
	}

}


