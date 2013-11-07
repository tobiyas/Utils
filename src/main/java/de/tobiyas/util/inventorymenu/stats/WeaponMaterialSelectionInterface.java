package de.tobiyas.util.inventorymenu.stats;

import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import de.tobiyas.util.inventorymenu.BasicSelectionInterface;

public class WeaponMaterialSelectionInterface extends MaterialSelectionInterface {

	public WeaponMaterialSelectionInterface(Player player,
			BasicSelectionInterface parent, Map<String, Object> config,
			String key, JavaPlugin plugin) {
		super(player, parent, config, key, plugin);
	}
	
	
	@Override
	protected boolean filter(ItemStack item) {
		switch(item.getType()){
		
		case WOOD_AXE : 
		case STONE_AXE : 
		case GOLD_AXE : 
		case IRON_AXE : 
		case DIAMOND_AXE : 

		case WOOD_SWORD : 
		case STONE_SWORD : 
		case GOLD_SWORD : 
		case IRON_SWORD : 
		case DIAMOND_SWORD : return true;
		
		
		default: return false;
		}
	}

}
