package de.tobiyas.util.items;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class ItemBuilder {

	
	private Material mat = Material.STONE;
	private short damage = 0;
	private int amount = 1;
	private String name = null;
	private List<String> lore = null;
	private Map<Enchantment,Integer> enchants = new HashMap<Enchantment,Integer>();
	
	
	public Material getMat() {
		return mat;
	}
	public ItemBuilder setMat(Material mat) {
		this.mat = mat;
		return this;
	}
	public short getDamage() {
		return damage;
	}
	public ItemBuilder setDamage(short damage) {
		this.damage = damage;
		return this;
	}
	public String getName() {
		return name;
	}
	public ItemBuilder setName(String name) {
		if(name != null) this.name = ChatColor.translateAlternateColorCodes('&', name);
		return this;
	}
	public List<String> getLore() {
		return lore;
	}
	public ItemBuilder setAmount(int amount) {
		this.amount = amount;
		return this;
	}
	public ItemBuilder setLore(List<String> lore) {
		this.lore = translateChatColors(lore);
		return this;
	}
	public ItemBuilder setLore(String... lore) {
		return setLore(Arrays.asList(lore));
	}
	
	public ItemBuilder setEnchants(List<String> enchantString) {
		for(String enchant : enchantString){
			String[] split = enchant.split(Pattern.quote(":"));
			if(split.length != 2) continue;
			
			Enchantment en = Enchantment.getByName(split[0]);
			int level = 0;
			try{level = Integer.parseInt(split[1]);} catch(Throwable exp){}
			
			if(level <= 0 || en == null) continue;
			enchants.put(en, level);
		}
		
		return this;
	}
	
	public ItemBuilder setEnchants(Map<Enchantment,Integer> enchants) {
		this.enchants.clear();
		this.enchants.putAll(enchants);
		
		return this;
	}
	
	/**
	 * Builds the Item.
	 */
	public ItemStack build(){
		ItemStack item = new ItemStack(mat, amount, damage);
		ItemMeta meta = item.getItemMeta();
		if(meta != null){
			if(name != null) meta.setDisplayName(name);
			if(lore != null && !lore.isEmpty()) meta.setLore(lore);
			if(!enchants.isEmpty()) {
				for(Map.Entry<Enchantment, Integer> entry : enchants.entrySet()){
					meta.addEnchant(entry.getKey(), entry.getValue(), true);
				}
			}
			
			item.setItemMeta(meta);
		}
		
		return item;
	}
	
	
	private List<String> translateChatColors(List<String> list){
		for(int i=0;i<list.size();i++) list.set(i, ChatColor.translateAlternateColorCodes('&', list.get(i)));
		return list;
	}
	
}
