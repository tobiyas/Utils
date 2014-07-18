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
package de.tobiyas.util.autocomplete;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import de.tobiyas.util.player.PlayerUtils;


public class AutoCompleteUtils {

	
	
	/**
	 * Returns a list of all AutoCompletes from the passed list.
	 * 
	 * @param names the names to check
	 * @param pre to check against
	 * 
	 * @return the list with the auto completed values.
	 */
	public static List<String> getAllNamesWith(Collection<String> names, String pre){
		List<String> list = new LinkedList<String>();
		
		for(String name : names){
			if(name.toLowerCase().startsWith(pre.toLowerCase())
					|| "".equals(pre)){
				list.add(name);
			}
		}
		
		return list;
	}
	
	
	/**
	 * Return a list of all TriggerTypes beginning with the pre passed.
	 * 
	 * @param pre to check
	 * @return list of TriggerTypes starting with the pre passed
	 */
	public static List<String> getAllEntitiesWith(String pre){
		List<String> list = new LinkedList<String>();
		
		for(EntityType entityName : EntityType.values()){
			if(entityName.name().toLowerCase().startsWith(pre.toLowerCase())
					|| "".equals(pre)){
				list.add(entityName.name());
			}
		}
		
		return list;
	}
	
	/**
	 * Return a list of all TriggerTypes beginning with the pre passed.
	 * 
	 * @param pre to check
	 * @return list of TriggerTypes starting with the pre passed
	 */
	public static List<String> getAllMonsterWith(String pre){
		List<String> list = new LinkedList<String>();
		
		for(EntityType entityName : EntityType.values()){
			if(entityName.name().toLowerCase().startsWith(pre.toLowerCase())
					|| "".equals(pre)){
				list.add(entityName.name());
			}
		}
		
		return list;
	}
	


	/**
	 * Gets all Materials starting with the pre.
	 * 
	 * @param pre to filter to
	 * @return the list of all materials for AutoComplete
	 */
	public static List<String> getAllMaterialsWith(String pre) {
		List<String> list = new LinkedList<String>();
		
		for(Material material : Material.values()){
			if(material.name().toLowerCase().startsWith(pre.toLowerCase())
					|| "".equals(pre)){
				list.add(material.name());
			}
		}
		
		return list;
	}
	
	/**
	 * Gets all BLOCK Materials starting with the pre.
	 * 
	 * @param pre to filter to
	 * @return the list of all materials for AutoComplete
	 */
	public static List<String> getAllBlockMaterialsWith(String pre) {
		List<String> list = new LinkedList<String>();
		
		for(Material material : Material.values()){
			if(!material.isBlock()) continue;
			
			if(material.name().toLowerCase().startsWith(pre.toLowerCase())
					|| "".equals(pre)){
				list.add(material.name());
			}
		}
		
		return list;
	}
	
	/**
	 * Gets all NON BLOCK Materials starting with the pre.
	 * 
	 * @param pre to filter to
	 * @return the list of all materials for AutoComplete
	 */
	public static List<String> getAllNonBlockMaterialsWith(String pre) {
		List<String> list = new LinkedList<String>();
		
		for(Material material : Material.values()){
			if(material.isBlock()) continue;
			
			if(material.name().toLowerCase().startsWith(pre.toLowerCase())
					|| "".equals(pre)){
				list.add(material.name());
			}
		}
		
		return list;
	}


	/**
	 * Returns all Players starting with the pre.
	 * 
	 * @param pre to search
	 * @return the list of all players beginning with the pre
	 */
	public static List<String> getAllPlayersWith(String pre) {
		pre = pre.toLowerCase();
		List<String> names = new LinkedList<String>();
		
		try{
			for(Player player : PlayerUtils.getOnlinePlayers()){
				if(player.getName().toLowerCase().startsWith(pre)){
					names.add(player.getName());
				}
			}
		}catch(Throwable exp) {}
		
		return names;
	}
	
}
