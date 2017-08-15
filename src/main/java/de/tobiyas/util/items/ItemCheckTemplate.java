package de.tobiyas.util.items;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tobiyas.util.formating.ParseUtils;

public class ItemCheckTemplate {
	
	
	private static final ItemCheckTemplate ALWAYS_TRUE_TEMPLATE = new ItemCheckTemplate(Material.AIR, 0, 0, "", null){
		
		@Override public boolean appliable(ItemStack item) {
		return true;
		}
		
	};
	
	
	private final Material type;
	private final short damage;
	private final int amount;
	
	private final String name;
	private final Collection<String> lore = new ArrayList<String>();
	
	
	
	private ItemCheckTemplate(Material type, int damage, int amount, String name, Collection<String> lore) {
		this(type, (short)damage, amount, name, lore);
	}
	
	private ItemCheckTemplate(Material type, short damage, int amount, String name, Collection<String> lore) {
		super();
		
			
		this.type = type;
		this.damage = damage;
		this.amount = amount;
		
		this.name = name;
		if(lore != null && !lore.isEmpty()) {
			for(String line : lore){
				this.lore.add(ChatColor.stripColor(line).toLowerCase());
			}
		}
	}
	
	
	/**
	 * If the item is appliable.
	 * @param item to check.
	 * @return true if appliable.
	 */
	public boolean appliable(ItemStack item){
		if(item == null) return false;
		
		//Check for usual data:
		if(item.getType() != type) return false;
		if(damage >= 0 && item.getDurability() != damage) return false;
		if(item.getAmount() < amount) return false;
		
		//Check for the meta:
		ItemMeta meta = item.getItemMeta();
		if(meta == null && !name.isEmpty()) return false;
		if(!meta.hasDisplayName() && !name.isEmpty()) return false;
		if(!name.isEmpty() && !meta.getDisplayName().equals(name)) return false;
		
		//Check if lore is present:
		if(!lore.isEmpty()) {
			Collection<String> left = new ArrayList<String>(lore);
			for(String line : meta.getLore()){
				left.remove(ChatColor.stripColor(line).toLowerCase());
			}
			
			if(!left.isEmpty()) return false;
		}
		
		return true;
	}
	
	
	/**
	 * Generates from a line of Text, split by ';' :
	 * Template: 'TYPE;DAMAGE;AMOUNT;NAME;LORE'
	 * 
	 * @param line to check against.
	 * @return the generated item.
	 */
	public static ItemCheckTemplate generate(String line){
		if(line == null || line.isEmpty()) return ALWAYS_TRUE_TEMPLATE;
		
		String[] parts = line.split(Pattern.quote(";"));
		Material type = ParseUtils.parseMaterial(parts, 0, Material.AIR);
		short damage = ParseUtils.parseShort(parts, 1, (short)-1);
		int amount = ParseUtils.parseInt(parts, 2, 1);
		
		String name = ParseUtils.parseString(parts, 3, "");
		Collection<String> lore = new ArrayList<String>();
		
		return new ItemCheckTemplate(type, damage, amount, name, lore);
	}
	
	
	/**
	 * Gets the Always True template.
	 * @return the always true template.
	 */
	public static ItemCheckTemplate TRUE(){
		return ALWAYS_TRUE_TEMPLATE;
	}
	

}
