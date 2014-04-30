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
package de.tobiyas.util.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tobiyas.util.UtilsUsingPlugin;
import de.tobiyas.util.config.returncontainer.DropContainer;


public class YAMLConfigExtended extends YamlConfiguration {

	/**
	 * If the Config is dirty and has to be flushed.
	 */
	private boolean dirty = false;
	
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
	 * If the file may be auto 
	 */
	private int autoReloadableSchedulerID = -1;
	
	
	/**
	 * This is for non persistent files.
	 */
	public YAMLConfigExtended() {
		super();
	}
	
	
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
	 * <br>Returns true if no path for saving is passed.
	 * 
	 * @return true if saving worked or if nothing was to save.
	 */
	public boolean save(){
		if(!dirty) return true;
		if(totalPath == null) return true;
		
		File file = fileCheck();
		try {
			this.save(file);
			this.dirty = false;
			
			this.lastChangeDate = new Date(file.lastModified());
		} catch (IOException e) {
			return false;
		}
		
		return true;
	}

	/**
	 * Saves the Config to the File passed in construction.
	 * This is an ASYNC Operation!!!
	 */
	public void saveAsync(){
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				try{
					save();
					dirty = false;
				}catch(Exception exp){
					//Ignore all saving exceptions.
				}
				return;
			}
		};
		
		new Thread(null, runnable, "ConfigSavingThread").start();
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
			
			if(isBoolean("utf8") && getBoolean("utf8")) {
				return loadCharset(saveFile, "UTF-8");
			}
			if(isBoolean("ansi") && getBoolean("ansi")) {
				return loadCharset(saveFile, "windows-1252");
			}
			
			this.lastChangeDate = new Date(savePathFile.lastModified());
		} catch (Exception e) {
			validLoad = false;
			System.out.println("Error on loading YamlConfig: " + saveFile.getAbsolutePath());
			return this;
		}
		
		validLoad = true;
		dirty = false;
		return this;
	}
	
	
	/**
	 * Loads with a specific Charset.
	 * 
	 * @param file to load from.
	 * @param charset to load.
	 * 
	 * @return the loaded file.
	 */
	private YAMLConfigExtended loadCharset(File file, String charset){
		try {
			InputStreamReader reader = new InputStreamReader(new FileInputStream(file), Charset.forName(charset));
			StringBuilder builder = new StringBuilder();
			BufferedReader input = new BufferedReader(reader);
			
			
			try {
				String line;
				
				while ((line = input.readLine()) != null) {
					builder.append(line);
					builder.append('\n');
				}
			} finally {
				input.close();
			}
			
			loadFromString(builder.toString());
			this.validLoad = true;
		} catch (Throwable e) {
			try {
				load(file);
			} catch (Throwable exp) {}
			return this;
		}
		
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
	
	
	/**
	 * Returns if the Config is dirty or not.
	 * 
	 * @return true if is dirty
	 */
	public boolean isDirty(){
		return dirty;
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
		if(object != null && object.equals(this.get(path))) return; //we already have this value here.
		
		//setting anything means that the config is not sync to disc.
		dirty = true;
		
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
		
		if(object instanceof ItemStack){
			setItemStack(path, (ItemStack) object);
			return;
		}
		
		super.set(path, object);
	}
	
	
	
	@Override
	public String getString(String path) {
		String result = super.getString(path);
		return replaceUmlauts(result);
	}


	@Override
	public String getString(String path, String def) {
		String result = super.getString(path, def);
		return replaceUmlauts(result);
	}
	
	
	@Override
	public List<String> getStringList(String path) {
		List<String> stringList = super.getStringList(path);
		List<String> newStringList = new LinkedList<String>();
		
		for(String string : stringList){
			string = replaceUmlauts(string);
			newStringList.add(string);
		}
		
		return newStringList;
	}


	/**
	 * Replaces all Umlauts with the correct ones.
	 * 
	 * @param toReplace the string to replace
	 * 
	 * @return the replaces String
	 */
	private String replaceUmlauts(String toReplace){
		if(toReplace == null) return null;
		
		toReplace = toReplace.replace(new String("�".getBytes(Charset.forName("UTF-8"))), "�");
		toReplace = toReplace.replace(new String("�".getBytes(Charset.forName("UTF-8"))), "�");

		toReplace = toReplace.replace(new String("�".getBytes(Charset.forName("UTF-8"))), "�");
		toReplace = toReplace.replace(new String("�".getBytes(Charset.forName("UTF-8"))), "�");
		
		toReplace = toReplace.replace(new String("�".getBytes(Charset.forName("UTF-8"))), "�");
		toReplace = toReplace.replace(new String("�".getBytes(Charset.forName("UTF-8"))), "�");
		
		return toReplace;
	}
	

	/**
	 * Saves a Location to the given Path.
	 * 
	 * @param path 
	 * @param location
	 */
	public void setLocation(String path, Location location){
		if(location == null){
			set(path, null);
			return;
		}
		
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
		if(container == null){
			set(path, null);
			return;
		}
		
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
		if(material == null){
			set(path, null);
			return;
		}
		
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
	@SuppressWarnings("deprecation") //Hopefully never removed
	public Material getMaterial(String path, Material defaultMaterial){
		if(isInt(path)){
			try{
				return Material.getMaterial(getInt(path));
			}catch (IllegalArgumentException exp){
				return defaultMaterial;
			}
		}
		
		if(isString(path)){
			try{
				Material mat = Material.valueOf(getString(path).toUpperCase());
				return mat == null ? defaultMaterial : mat;	
			}catch(IllegalArgumentException exp){			
				return defaultMaterial;
			}
		}
		
		return defaultMaterial;
	}
	

	/**
	 * ItemStack To save
	 * 
	 * @param pre to save to
	 * @param item to save
	 */
	public void setItemStack(String pre, ItemStack item){
		if(item == null){
			set(pre, null);
			return;
		}
		
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
				
				//replace color codes.
				if(obj instanceof String){
					obj = ChatColor.translateAlternateColorCodes('&', (String)obj );
				}
				
				serialized.put(data, obj);
			}
			
			ItemStack item = ItemStack.deserialize(serialized);
			if(item.hasItemMeta()){
				ItemMeta meta = item.getItemMeta();
				if(meta.hasDisplayName()){
					meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', meta.getDisplayName()));
				}
				
				if(meta.hasLore()){
					List<String> lore = meta.getLore();
					for(int i = 0; i < lore.size(); i++){
						lore.set(i, ChatColor.translateAlternateColorCodes('&', lore.get(i)));
					}
					meta.setLore(lore);
				}
				
				item.setItemMeta(meta);
			}
			
			return item;
		}catch(Exception exp){
			return defaultStack;
		}
	}
	
	/**
	 * Loads the Content from the String passed.
	 * 
	 * @param contents to load
	 * @return the Config for Chaining calls.
	 */
	public YAMLConfigExtended loadSafeFromString(String contents) {
		
		try{
			super.loadFromString(contents);
			dirty = false;
		}catch(Exception exp){
			validLoad = false;
		}
		
		return this;
	}

	/**
	 * Returns the File of the Config loaded from.
	 * <br>Can be null if Config is inited with no File.
	 * 
	 * @return file of the Config or null.
	 */
	public File getFileLoadFrom(){
		if(totalPath == null){
			return null;
		}
		
		return new File(totalPath);
	}
	
	
	/**
	 * The last changeDate.
	 */
	private Date lastChangeDate = null;
	
	
	/**
	 * Sets if the File should be auto-reloaded on change.
	 * 
	 * @param autoReloadable true if relaod on change, false otherwise.
	 */
	@SuppressWarnings("deprecation")
	public void setAutoReloadable(boolean autoReloadable, UtilsUsingPlugin plugin){
		if(this.autoReloadableSchedulerID != -1){
			Bukkit.getScheduler().cancelTask(autoReloadableSchedulerID);
			this.autoReloadableSchedulerID = -1;
		}
		
		if(autoReloadable){
			this.autoReloadableSchedulerID = Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, new Runnable() {
				
				@Override
				public void run() {
					File savePathFile = new File(totalPath);
					if(savePathFile.exists()){
						Date lastChangeDate = new Date(savePathFile.lastModified());
						if(lastChangeDate.after(YAMLConfigExtended.this.lastChangeDate)){
							load();
						}
					}
				}
			}, 20, 20);
		}
		
	}
	
	
	public boolean isAutoReloadable(){
		return this.autoReloadableSchedulerID != -1;
	}
	
	
	
	/**
	 * Warning: This removes EVERYTHING in this config!
	 */
	public void clearConfig(){
		this.map.clear();
		this.dirty = true;
	}
}


