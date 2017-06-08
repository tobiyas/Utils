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
package de.tobiyas.util.items;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tobiyas.util.collections.CollectionUtils;
import de.tobiyas.util.formating.ParseUtils;

public class ItemUtils {

	public enum ItemQuality{
		None(-1),
		Leather(0),
		Iron(1),
		Gold(2),
		Diamond(3),
		Chain(4);
		
		private int value;
		
		ItemQuality(int value){
			this.value = value;
		}
		
		public int getValue(){
			return value;
		}
		
	}
	
	
	public enum WeaponToolQuality{
		None(-1),
		Wood(0),
		Stone(1),
		Iron(2),
		Gold(3),
		Diamond(4);
		
		private int value;
		
		WeaponToolQuality(int value){
			this.value = value;
		}
		
		public int getValue(){
			return value;
		}
		
	}
	
	
	/**
	 * Gets the Item Quality of a piece of armor
	 * 
	 * @param stack to check
	 * @return
	 */
	public static ItemQuality getItemValue(ItemStack stack){
		switch(stack.getType()){
			case LEATHER_HELMET :
			case LEATHER_CHESTPLATE :
			case LEATHER_LEGGINGS :
			case LEATHER_BOOTS : return ItemQuality.Leather;
			
			case CHAINMAIL_HELMET :
			case CHAINMAIL_CHESTPLATE :
			case CHAINMAIL_LEGGINGS :
			case CHAINMAIL_BOOTS : return ItemQuality.Chain;
			
			case IRON_HELMET :
			case IRON_CHESTPLATE :
			case IRON_LEGGINGS :
			case IRON_BOOTS : return ItemQuality.Iron;
			
			case DIAMOND_HELMET :
			case DIAMOND_CHESTPLATE :
			case DIAMOND_LEGGINGS :
			case DIAMOND_BOOTS : return ItemQuality.Diamond;
			
			case GOLD_HELMET :
			case GOLD_CHESTPLATE :
			case GOLD_LEGGINGS :
			case GOLD_BOOTS : return ItemQuality.Gold;
		
			default: return ItemQuality.None;
		}
	}
	
	/**
	 * Gets the Item Quality of a piece of armor
	 * 
	 * @param stack to check
	 * @return
	 */
	public static WeaponToolQuality getToolWeaponValue(Material mat){
		switch(mat){
			case WOOD_SPADE :
			case WOOD_AXE :
			case WOOD_HOE :
			case WOOD_PICKAXE : 
			case WOOD_SWORD : return WeaponToolQuality.Wood;
			
			case STONE_SPADE :
			case STONE_AXE :
			case STONE_HOE :
			case STONE_PICKAXE : 
			case STONE_SWORD : return WeaponToolQuality.Stone;
			
			case IRON_SPADE :
			case IRON_AXE :
			case IRON_HOE :
			case IRON_PICKAXE : 
			case IRON_SWORD : return WeaponToolQuality.Iron;
			
			case GOLD_SPADE :
			case GOLD_AXE :
			case GOLD_HOE :
			case GOLD_PICKAXE : 
			case GOLD_SWORD : return WeaponToolQuality.Gold;
			
			case DIAMOND_SPADE :
			case DIAMOND_AXE :
			case DIAMOND_HOE :
			case DIAMOND_PICKAXE : 
			case DIAMOND_SWORD : return WeaponToolQuality.Diamond;
		
			default: return WeaponToolQuality.None;
		}
	}
	
	
	/**
	 * Returns the robustness of an Item.
	 * This is only applied for Armor.
	 * 
	 * @param stack to check
	 * @return int representation of robustness
	 */
	public static int getArmorValueOfItem(ItemStack stack){
		if(stack == null || stack.getType() == Material.AIR) return 0;
		
		switch(stack.getType()){
			//LeatherArmor stuff
			case LEATHER_BOOTS: return 1;
			case LEATHER_HELMET: return 1;
			case LEATHER_LEGGINGS: return 2;
			case LEATHER_CHESTPLATE: return 3;
			
			//GoldArmor stuff
			case GOLD_BOOTS: return 1;
			case GOLD_HELMET: return 2;
			case GOLD_LEGGINGS: return 3;
			case GOLD_CHESTPLATE: return 5;
			
			//ChainArmor stuff
			case CHAINMAIL_BOOTS: return 1;
			case CHAINMAIL_HELMET: return 2;
			case CHAINMAIL_LEGGINGS: return 4;
			case CHAINMAIL_CHESTPLATE: return 5;
			
			//IronArmor stuff
			case IRON_BOOTS: return 2;
			case IRON_HELMET: return 2;
			case IRON_LEGGINGS: return 5;
			case IRON_CHESTPLATE: return 6;
			
			//DiamondArmor stuff
			case DIAMOND_BOOTS: return 3;
			case DIAMOND_HELMET: return 3;
			case DIAMOND_LEGGINGS: return 6;
			case DIAMOND_CHESTPLATE: return 8;
			
			default: return 0;
		}
	}
	
	
	/**
	 * Linked to the Armor slots in Minecraft.
	 */
	public enum ArmorSlot{
		HELMET,
		CHESTPLATE,
		LEGGINGS,
		BOOTS,
		NONE;
	}
	
	/**
	 * Returns the armor slot the item can be equipped to.
	 * Returns -1 if NO armor item.
	 * 
	 * @param item
	 * @return
	 */
	public static ArmorSlot getItemSlotEquiping(ItemStack item){
		if(item == null){
			return ArmorSlot.NONE;
		}
		
		switch(item.getType()){
			//Helmets:	
			case LEATHER_HELMET:
			case GOLD_HELMET:
			case CHAINMAIL_HELMET:
			case IRON_HELMET:
			case DIAMOND_HELMET: return ArmorSlot.HELMET;
			
			//Chest:	
			case LEATHER_CHESTPLATE:
			case GOLD_CHESTPLATE:
			case CHAINMAIL_CHESTPLATE:
			case IRON_CHESTPLATE:
			case DIAMOND_CHESTPLATE: return ArmorSlot.CHESTPLATE;
			
			//Legs:	
			case LEATHER_LEGGINGS:
			case GOLD_LEGGINGS:
			case CHAINMAIL_LEGGINGS:
			case IRON_LEGGINGS:
			case DIAMOND_LEGGINGS: return ArmorSlot.LEGGINGS;
			
			//feet:	
			case LEATHER_BOOTS:
			case GOLD_BOOTS:
			case CHAINMAIL_BOOTS:
			case IRON_BOOTS:
			case DIAMOND_BOOTS: return ArmorSlot.BOOTS;
		
		
			default: return ArmorSlot.NONE;
		}
	}
	
	
	/**
	 * Gets the ItemStack in the Armor Inventory slot of a Player.
	 * Returns null if {@link ItemStack#NONE} is passed.
	 * 
	 * @param player to check
	 * @param slot to check
	 * 
	 * @return the itemStack in the slot
	 */
	public static ItemStack getItemInArmorSlotOfPlayer(Player player, ArmorSlot slot){
		if(slot == null){
			return null;
		}
		
		switch (slot) {
			case BOOTS:
				return player.getInventory().getBoots();
			
			case CHESTPLATE:
				return player.getInventory().getChestplate();
			
			case HELMET:
				return player.getInventory().getHelmet();
			
			case LEGGINGS:
				return player.getInventory().getLeggings();
			
			case NONE:
				return null;
		}
		
		//dead code.
		return null;
	}
	
	
	/**
	 * Generates an Item from a line.
	 * @param line to generate from.
	 * @return the item or null if not parseable.
	*/
	public static ItemStack generateFromLine(String line){
		return generateFromLine(line, new ItemStack(Material.AIR));
	}
	
	
	/**
	 * Generates an Item from a line.
	 * @param line to generate from.
	 * @param defaultItem the default item to return if failed.
	 * @return the item or null if not parseable.
	 */
	public static ItemStack generateFromLine(String line, ItemStack defaultItem){
		String[] split = line.split(Pattern.quote("#"));
		Material mat = null;
		short damage = 0;
		int amount = 1;
		String name = null;
		List<String> lore = new ArrayList<String>();
		Map<Enchantment,Integer> enchants = new HashMap<Enchantment,Integer>();
		boolean unbreakable = false;
		Set<ItemFlag> flags = new HashSet<ItemFlag>();
		
		//Only 1 split -> Material?!
		if(split.length == 1) mat = Material.matchMaterial(split[0]);
		
		//Go for the Splits!
		for(String part : split){
			if(!part.contains(":")) continue;
			
			String[] partSplit = part.split(Pattern.quote(":"), 2);
			if(partSplit.length != 2) continue;
			
			//Read the Stuff:
			String key = partSplit[0];
			String value = partSplit[1];
			
			//Now parse:
			//Material
			if(key.equalsIgnoreCase("material")){
				mat = Material.matchMaterial(value.toUpperCase());
				continue;
			}
			
			//Damage:
			if(key.equalsIgnoreCase("damage")){
				damage = ParseUtils.parseShort(value, (short)0);
				continue;
			}
			
			//Amount:
			if(key.equalsIgnoreCase("amount")){
				amount = ParseUtils.parseInt(value, 1);
				continue;
			}
			
			//Name:
			if(key.equalsIgnoreCase("name")){
				name = ChatColor.translateAlternateColorCodes('&', value);
				continue;
			}
			
			//lore:
			if(key.equalsIgnoreCase("lore")){
				String[] loreSplit = value.split(Pattern.quote("~"));
				lore.addAll(Arrays.asList(loreSplit));
				CollectionUtils.translateChatColors(lore);
				continue;
			}
			
			//enchant:
			if(key.equalsIgnoreCase("enchant")){
				if(value.trim().equals("true")){
					enchants.put(Enchantment.DURABILITY, 1);
					continue;
				}
				
				String[] enchantSplit = value.split(Pattern.quote("~"));
				for(String enchantLine : enchantSplit){
					String[] enchantSplit2 = enchantLine.split(Pattern.quote(":"));
					if(enchantSplit2.length != 2) continue;
					
					Enchantment ench = Enchantment.getByName(enchantSplit2[0]);
					int level = 1; ParseUtils.parseInt(value, 1);
					if(ench != null) enchants.put(ench,level);
				}
				
				continue;
			}
			
			//Parse the Item-Flags:
			if(key.equalsIgnoreCase("flags")){
				String[] flagSplit = value.split("~");
				for(String flagPart : flagSplit){
					try{
						ItemFlag flag = ItemFlag.valueOf(flagPart.toUpperCase());
						if(flag != null) flags.add(flag);
					}catch(Throwable exp){}
				}
			}
			
			//Parse unbreakable:
			if(key.equalsIgnoreCase("unbreakable")){
				unbreakable = ParseUtils.parseBoolean(value.trim(), false);
				if(unbreakable) flags.add(ItemFlag.HIDE_UNBREAKABLE);
				continue;
			}
		}
		
		//If no mat found -> Return default value:
		if(mat == null) return defaultItem;
		
		//Now generate the Item:
		ItemStack item = new ItemStack(mat, amount, damage);
		ItemMeta meta = item.getItemMeta();
		if(meta != null){
			//Add Name:
			if(name != null) meta.setDisplayName(name);
			
			//add lore:
			if(!lore.isEmpty()) meta.setLore(lore);
			
			//Add item flags:
			if(!flags.isEmpty()) meta.addItemFlags(flags.toArray(new ItemFlag[flags.size()]));
			
			//Add unbreakable if Spigot is present.
			try{
				Object spigot = meta.getClass().getMethod("spigot").invoke(meta);
				spigot.getClass().getMethod("setUnbreakable", boolean.class).invoke(spigot, unbreakable);
			}catch(Throwable exp){}
			
			//Add enchantments:
			if(!enchants.isEmpty()){
				for(Map.Entry<Enchantment,Integer> entry : enchants.entrySet()){
					meta.addEnchant(entry.getKey(), entry.getValue(), true);
				}
			}
		}
		
		item.setItemMeta(meta);
		return item;
	}
	
	
	/**
	 * Sets the item unbreakable.
	 * @param item to set
	 * @param unbreakable to set.
	 */
	public static void setUnbreakable(ItemStack item, boolean unbreakable){
		try{
			ItemMeta meta = item.getItemMeta();
			Method method = meta.getClass().getDeclaredMethod("spigot");
			Object spigot = method.invoke(meta);
			Method method2 = spigot.getClass().getMethod("setUnbreakable", boolean.class);
			method2.invoke(spigot, unbreakable);
			
			item.setItemMeta(meta);
		}catch(Throwable exp){}
	}
	
}
