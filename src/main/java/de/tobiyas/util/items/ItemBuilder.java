package de.tobiyas.util.items;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class ItemBuilder {

	
	private Material mat = Material.STONE;
	private short damage = 0;
	private int amount = 1;
	private String name = null;
	private List<String> lore = null;
	
	
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
		this.name = name;
		return this;
	}
	public List<String> getLore() {
		return lore;
	}
	public ItemBuilder setLore(List<String> lore) {
		this.lore = lore;
		return this;
	}
	public ItemBuilder setLore(String... lore) {
		this.lore = Arrays.asList(lore);
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
			if(lore != null) meta.setLore(lore);
			item.setItemMeta(meta);
		}
		
		return item;
	}
	
	
}
