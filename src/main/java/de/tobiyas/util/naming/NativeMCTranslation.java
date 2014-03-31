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
package de.tobiyas.util.naming;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Properties;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;


/**
 * Languages have to be copied to the root of the MC dir + {@value NativeMCTranslation#LANG_DIR_NAME}.
 * Files need to be in .lang format.
 * Therefore see in the Minecraft client:
 * 
 * %APPDATA%/.minecraft/assets/virtual/legacy/lang/
 * 
 * 
 * @author Tobiyas
 *
 */
public class NativeMCTranslation {
	
	/**
	 * The language Dir name
	 */
	private final static String LANG_DIR_NAME = "lang";
	
	
	/**
	 * Returns a pretty entity type name.
	 * <br>For languages see the Doc to this class.
	 * 
	 * 
	 * @param entityType to translate
	 * @param language to translate to.
	 * 
	 * @return the translated entity type
	 */
	@SuppressWarnings("deprecation") //as long as this works, it's fine.
	public static String getEntityTypeName(EntityType entityType, String language) {
		try{
			return entityType.getName();
		}catch(Throwable exp){
			return entityType.name();
		}
	}
	
	
	/**
	 * Translates a ItemStack for the client.
	 * 
	 * @param toTranslate to translate
	 * @return the translated String.
	 */
	public static String getItemTranslation(ItemStack item, String language){		
		try{
			char splitter = '.';
			Class<?> craftItemClass = Class.forName("org" + splitter + "bukkit" + splitter +"craftbukkit" + splitter 
					+ getCBReloc() + splitter + "inventory" + splitter + "CraftItemStack");
			
			Method method = craftItemClass.getMethod("asNMSCopy", ItemStack.class);
			Object nmsItemStack = method.invoke(null, item);
			
			Method getItem = nmsItemStack.getClass().getMethod("getItem");
			Object nmsItem = getItem.invoke(nmsItemStack);
			
			Method getName = nmsItem.getClass().getMethod("k", nmsItemStack.getClass());
			String itemName = (String) getName.invoke(nmsItem, nmsItemStack);
			
			return getTranslation(itemName + ".name", language);
		}catch(Throwable exp){
			return item.getType().name();
		}
	}
	
	
	/**
	 * Returns the translation from Bukkit translate.
	 * 
	 * @param toTranslate 
	 * 
	 * @return the translation or the passed value if not found.
 	 */
	public static String getTranslation(String toTranslate){
		try{
			char splitter = '.';
			Class<?> locali18n = Class.forName("net" + splitter + "minecraft" + splitter + "server" + splitter + getCBReloc() 
					+ splitter + "LocaleI18n");
			Method method = locali18n.getMethod("get", String.class);
			return (String) method.invoke(null, toTranslate);
		}catch(Throwable exp){
			return toTranslate;
		}
	}
	
	/**
	 * Returns the translation from Bukkit translate.
	 * 
	 * @param toTranslate 
	 * 
	 * @return the translation or the passed value if not found.
 	 */
	public static String getTranslation(String toTranslate, String language){
		try{
			File mcFile = new File(Server.class.getProtectionDomain().getCodeSource().getLocation().getPath());
			File currentDir = mcFile.getParentFile();
			currentDir = new File(currentDir.getAbsolutePath().replaceAll("%20", " "));

			File langDir = new File(currentDir, LANG_DIR_NAME);
			File langFile = new File(langDir, language + ".lang");
			
			//early out for not existing language.
			if(!langFile.exists()) return getTranslation(toTranslate);
			
			Properties properties = new Properties();
			properties.load(new FileInputStream(langFile));
			
			if(properties.containsKey(toTranslate)){
				return properties.getProperty(toTranslate);
			}else{
				return getTranslation(toTranslate);
			}
			
		}catch(Throwable exp){
			return getTranslation(toTranslate);
		}
	}
	
	
	/**
	 * Returns the CB Relocation String
	 */
	private static String getCBReloc(){
		try{
			String bukkitPath = Bukkit.getServer().getClass().getPackage().getName();
			
			String[] split = bukkitPath.split("\\.");
			return split[split.length - 1];
		}catch(Throwable exp){return "";}
	}

}
