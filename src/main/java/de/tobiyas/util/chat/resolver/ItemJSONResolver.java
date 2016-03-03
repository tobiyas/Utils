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
package de.tobiyas.util.chat.resolver;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;

import com.google.gson.JsonObject;

import de.tobiyas.util.naming.MCPrettyName;
import de.tobiyas.util.vollotile.VollotileCodeManager;

public class ItemJSONResolver {

	/**
	 * Generates an Item Text for the Item.
	 * 
	 * @param itemToParse to parse.
	 * @return the item to parse.
	 */
	@SuppressWarnings("deprecation")
	public static String getHoverText(ItemStack itemToParse){
		if(itemToParse == null) return "";
		
		String flatLore = "";
		//Read lore if present.
		if(itemToParse.hasItemMeta() && itemToParse.getItemMeta().hasLore()){
			List<String> lore = itemToParse.getItemMeta().hasLore() ? itemToParse.getItemMeta().getLore() : new LinkedList<String>();
			flatLore = ",Lore:["; 
			Iterator<String> loreIt = lore.iterator();
			while(loreIt.hasNext()){flatLore += "\\\"" + loreIt.next().replace(":", '\u00a6'+"").replace("\"", "\\\"") + "\\\""; if(loreIt.hasNext()) flatLore += ",";}
			flatLore += "]";
		}
		
		String name = MCPrettyName.getPrettyName(itemToParse, "de_DE");
		String idString = VollotileCodeManager.getVollotileCode().generateItemIDString(itemToParse);
		short damage = itemToParse.getDurability();
		
		String enchantFormat = "";
		//Read enchants if present
		if(!itemToParse.getEnchantments().isEmpty()){
			enchantFormat = ",ench:[";
			Iterator<Entry<Enchantment,Integer>> entryIt = itemToParse.getEnchantments().entrySet().iterator();
			while(entryIt.hasNext()){
				Entry<Enchantment, Integer> entry = entryIt.next();
				enchantFormat += "{id:" + entry.getKey().getId() + ",lvl:" + entry.getValue() + "}";
			
				if(entryIt.hasNext()){
					enchantFormat += ",";
				}
			}
			
			enchantFormat += "]";
		}
		
		
		String storedEnchants = "";
		//StoredEnchantments for Enchantment books
		if(itemToParse.getType() == Material.ENCHANTED_BOOK && itemToParse.hasItemMeta()){
			EnchantmentStorageMeta enchantedMeta = (EnchantmentStorageMeta) itemToParse.getItemMeta();
			
			storedEnchants = ",StoredEnchantments:[";
			Iterator<Entry<Enchantment,Integer>> entryIt = enchantedMeta.getStoredEnchants().entrySet().iterator();
			while(entryIt.hasNext()){
				Entry<Enchantment, Integer> entry = entryIt.next();
				storedEnchants += "{id:" + entry.getKey().getId() + ",lvl:" + entry.getValue() + "}";
			
				if(entryIt.hasNext()){
					storedEnchants += ",";
				}
			}
			
			storedEnchants += "]";
		}
		
		
		String potionEffects = "";
		//parse Potion effects
		if(itemToParse.getType() == Material.POTION && itemToParse.hasItemMeta()){
			PotionMeta potionMeta = (PotionMeta) itemToParse.getItemMeta();
			
			potionEffects = ",CustomPotionEffects:[";
			Iterator<PotionEffect> entryIt = potionMeta.getCustomEffects().iterator();
			while(entryIt.hasNext()){
				PotionEffect effect = entryIt.next();
				potionEffects += "{id:" + effect.getType().getId() + ",Amplifier:" + effect.getAmplifier() 
						+ ",Duration:" + effect.getDuration() + ",Ambient:" + (effect.isAmbient() ? 1 : 0) + "}";
			
				if(entryIt.hasNext()){
					potionEffects += ",";
				}
			}
			
			potionEffects += "]";
		}
		
		String skullAdd = "";
		//Skull infos
		if(itemToParse.getType() == Material.SKULL_ITEM && itemToParse.hasItemMeta() && itemToParse.getDurability() == 3
				&& ((SkullMeta)itemToParse.getItemMeta()).hasOwner()){
			skullAdd = ",SkullOwner:\\\"" + ((SkullMeta)itemToParse.getItemMeta()).getOwner() + "\\\"";
		}
		
		String bookAdd = "";
		//Book infos
		if(itemToParse.getType() == Material.WRITTEN_BOOK && itemToParse.hasItemMeta()){
			BookMeta meta = (BookMeta) itemToParse.getItemMeta();
			boolean hasAuthor = meta.hasAuthor();
			boolean hasTitle = meta.hasTitle();
			
			if( meta.hasAuthor() ) bookAdd += ",author:\\\"" + meta.getAuthor() + "\\\"";
			if(hasAuthor && hasTitle) bookAdd += ",";
			
			if( meta.hasTitle() ) bookAdd += "title:\\\"" + meta.getTitle() + "\\\"";
			//TODO add pages.
		}
		
		
		return  "{"
				+ "id:" + idString + ",Damage:" + damage + ",tag:"
				+ "{"
					+ "display:"
					+ "{"
						+ "Name:\\\"" + name + "\\\"" + flatLore
					+ "}"
					+ enchantFormat
					+ storedEnchants
					+ potionEffects
					+ skullAdd
					+ bookAdd
				+ "}"
			+ "}";
	}
	


	/**
	 * Returns an Item Hovering Format.
	 * 
	 * @param itemToParse to parse
	 * @return the RAW text for sending to client
	 */
	public static String getItemRawHoverText(ItemStack itemToParse, String label){
		if(itemToParse == null){
			return "";
		}
		
		String hoverText = getHoverText(itemToParse);
		String colorName = "aqua";
		
		String hoverFormat = "{"
				+ "\"action\":\"show_item\",\"value\":\""
					+ hoverText
				+ "\"}";
		
		String name = MCPrettyName.getPrettyName(itemToParse, "de_DE");
		if(label == null || "".equals(label)) label = name;
		
		String colorCode = ChatColor.getLastColors(label);
		String itemFormat = "{\"text\":\"" + colorCode + "[" + label + "]" + colorCode + "\"," //TODO add colored braces
				+ "\"color\":\"" + colorName + "\","
				+ "\"hoverEvent\":" + hoverFormat + "}";
		
		return itemFormat;
	}


//	private static JsonObject getJsonHoverEvent(ItemStack itemToParse) {
//		JsonObject hoverObject = new JsonObject();
//		if(itemToParse == null) return hoverObject;
//		
//		//do the base structure first
//		hoverObject.addProperty("action", "show_item");
//
//		JsonObject itemDescription = new JsonObject();
//		JsonObject itemTag = new JsonObject();
//		JsonObject displayTag = new JsonObject();
//		
//		itemTag.add("display", displayTag);
//		itemDescription.add("tag", itemTag);
//		hoverObject.add("value", itemDescription);
//
//		//base structure done.
//		
//		itemDescription.addProperty("id", itemToParse.getTypeId());
//		itemDescription.addProperty("Damage", itemToParse.getDurability());
//		
//		//set name
//		String name = MCPrettyName.getPrettyName(itemToParse, "de_DE");
//		displayTag.addProperty("Name", name);
//		
//		
//		//add the Lore.
//		if(itemToParse.hasItemMeta() && itemToParse.getItemMeta().hasLore()){
//			List<String> lore = itemToParse.getItemMeta().hasLore() ? itemToParse.getItemMeta().getLore() : new LinkedList<String>();
//			JsonArray loreArray = new JsonArray();
//			for(String loreString : lore) loreArray.add(new JsonPrimitive(loreString));
//			displayTag.add("Lore", loreArray);
//		}
//		
//		//add Enchantments
//		if(!itemToParse.getEnchantments().isEmpty()){
//			JsonArray enchantArray = new JsonArray();
//			for(Entry<Enchantment, Integer> entry: itemToParse.getEnchantments().entrySet()){
//				JsonObject enchant = new JsonObject();
//				
//				enchant.addProperty("id", entry.getKey().getId());
//				enchant.addProperty("lvl", entry.getValue());
//				
//				enchantArray.add(enchant);
//			}
//			itemTag.add("ench", enchantArray);
//		}
//		
//		//add Stored Enchantments
//		if(itemToParse.getType() == Material.ENCHANTED_BOOK && itemToParse.hasItemMeta()){
//			EnchantmentStorageMeta enchantedMeta = (EnchantmentStorageMeta) itemToParse.getItemMeta();
//			
//			JsonArray storedEnchArray = new JsonArray();
//			for(Entry<Enchantment,Integer> entry : enchantedMeta.getStoredEnchants().entrySet()){
//				JsonObject enchant = new JsonObject();
//				
//				enchant.addProperty("id", entry.getKey().getId());
//				enchant.addProperty("lvl", entry.getValue());
//				
//				storedEnchArray.add(enchant);
//			}
//			itemTag.add("StoredEnchantments", storedEnchArray);
//		}
//		
//		//parse Potion effects
//		if(itemToParse.getType() == Material.POTION && itemToParse.hasItemMeta()){
//			PotionMeta potionMeta = (PotionMeta) itemToParse.getItemMeta();
//			
//			JsonArray potionEffects = new JsonArray();
//			for(PotionEffect entry : potionMeta.getCustomEffects()){
//				JsonObject effect = new JsonObject();
//				effect.addProperty("id", entry.getType().getId());
//				effect.addProperty("Amplifier", entry.getAmplifier());
//				effect.addProperty("Duration", entry.getDuration());
//				effect.addProperty("Ambient", entry.isAmbient()); //TODO maybe set 0 or 1
//
//				potionEffects.add(effect);
//			}
//			
//			itemTag.add("CustomPotionEffects", potionEffects);
//		}
//		
//		//Skull infos
//		if(itemToParse.getType() == Material.SKULL_ITEM && itemToParse.hasItemMeta() && itemToParse.getDurability() == 3
//				&& ((SkullMeta)itemToParse.getItemMeta()).hasOwner()){
//			
//			itemTag.addProperty("SkullOwner", ((SkullMeta)itemToParse.getItemMeta()).getOwner());
//		}
//		
//		
//		//Book infos
//		if(itemToParse.getType() == Material.WRITTEN_BOOK && itemToParse.hasItemMeta()){
//			BookMeta meta = (BookMeta) itemToParse.getItemMeta();
//			
//			itemTag.addProperty("author",  meta.getAuthor());
//			itemTag.addProperty("title",  meta.getTitle());
//		}
//
//		
//		System.out.println(hoverObject.toString());
//		return hoverObject;
//	}
	
	/**
	 * Add Hover Event to JSON.
	 * 
	 * @param itemToParse the item to parse.
	 * @param label to use.
	 * 
	 * @return the Chained JSON object.
	 */
	public static JsonObject addItemHover(JsonObject toAddTo, ItemStack itemToParse) {
		String hoverText = getHoverText(itemToParse);
		hoverText = hoverText.replace("\\\"", "\"");
		
		JsonObject showItem = new JsonObject();
		showItem.addProperty("action", "show_item");
		showItem.addProperty("color", "aqua");
		showItem.addProperty("value", hoverText);
		
		toAddTo.add("hoverEvent", showItem);
		return toAddTo;
	}

	
	public static JsonObject getItemRawHoverTextJSON(ItemStack itemToParse, String label) {
		if(itemToParse == null) return new JsonObject();
		if(label == null) label = MCPrettyName.getPrettyName(itemToParse, "de_DE");
		
		String colorCode = ChatColor.getLastColors(label);
		String itemLabel = colorCode + "[" + label + "]";
		
		JsonObject object = PlainTextJSONResolver.getRawFromPlainTextJSON(itemLabel);		
		return addItemHover(object, itemToParse);
	}

}
