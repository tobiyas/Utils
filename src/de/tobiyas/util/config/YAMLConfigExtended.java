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


public class YAMLConfigExtended extends YamlConfiguration {

	private String savePath;
	private boolean validLoad;
	
	public YAMLConfigExtended(String savePath){
		super();
		validLoad = false;
		this.savePath = savePath;
	}
	
	/**
	 * Util for YAMLReader to get all child-keys as Set<String> for a given Node 
	 * 
	 * @param yamlNode the Node to get the children from
	 * @return the children as Set<String>
	 */
	public Set<String> getYAMLChildren(String yamlNode){
		try{
			ConfigurationSection tempMem = getConfigurationSection(yamlNode);
			Set<String> tempSet = tempMem.getKeys(false);
			return tempSet;
			
		}catch(Exception e){
			Set<String> empty = new LinkedHashSet<String>();
			return empty;
		}
	}
	
	public boolean save(){
		File file = fileCheck(savePath);
		try {
			this.save(file);
		} catch (IOException e) {
			return false;
			//plugin.log("saving config failed.");
		}
		
		return true;
	}
	
	public YAMLConfigExtended load(){
		File savePathFile = new File(savePath);
		if(!savePathFile.exists())
			savePathFile.mkdir();
		
		File saveFile = fileCheck(savePath);
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
	
	public boolean getValidLoad(){
		return validLoad;
	}
	
	public boolean loadDelete(){
		File savePathFile = new File(savePath);
		if(!savePathFile.exists())
			savePathFile.mkdir();

		File saveFile = new File(savePath);
		if(!saveFile.exists()) return false;
			
		try {
			load(saveFile);
		} catch (Exception e) {
			return false;
			//plugin.log("Error on loading Enables");
		}
		
		return saveFile.delete();
	}
	
	private File fileCheck(String file){		
		File fileFile = new File(file);
		if(!fileFile.exists()){
			try {
				fileFile.createNewFile();
			} catch (IOException e) {
				//plugin.log("Error creating new File :" + file);
				return null;
			}
		}
		return fileFile;
	}
	
	public void setLocation(String path, Location location){
		double locX = location.getX();
		double locY = location.getY();
		double locZ = location.getZ();
		String world = location.getWorld().getName();
		
		createSection(path);
		set(path + ".x", locX);
		set(path + ".y", locY);
		set(path + ".z", locZ);
		set(path + ".world", world);
	}
	
	public Location getLocation(String path, Location defaultLocation){
		Double locX = getDouble(path + ".x");
		Double locY = getDouble(path + ".y");
		Double locZ = getDouble(path + ".z");
		
		String world = getString(path + ".world");
		if(locX == null || locY == null || locZ == null || world == null) return defaultLocation;
		
		World World = Bukkit.getWorld(world);
		if(World == null) return defaultLocation;
		
		Location loc = new Location(World, locX, locY, locZ);
		return loc;
	}
	
	public Location getLocation(String path){
		return getLocation(path, null);
	}
	
	public void setDrop(String path, int itemID, int damageValue, int minAmount, int maxAmount, double probability){
		String pre = path + "." + itemID;
		if(damageValue != -1)
			pre += "-" + damageValue;
		
		createSection(pre);
		set(pre + ".min", minAmount);
		set(pre + ".max", maxAmount);
		set(pre + ".probability", probability);
	}

}


