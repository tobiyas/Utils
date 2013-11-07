package de.tobiyas.util.inventorymenu.stats;

import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import de.tobiyas.util.inventorymenu.BasicSelectionInterface;

public class ToolMaterialSelectionInterface extends MaterialSelectionInterface {

	public ToolMaterialSelectionInterface(Player player,
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
		case DIAMOND_SWORD : 
			
		case WOOD_HOE : 
		case STONE_HOE : 
		case GOLD_HOE : 
		case IRON_HOE : 
		case DIAMOND_HOE : 
			
		case WOOD_SPADE : 
		case STONE_SPADE : 
		case GOLD_SPADE : 
		case IRON_SPADE : 
		case DIAMOND_SPADE : 
			
		case WOOD_PICKAXE : 
		case STONE_PICKAXE : 
		case GOLD_PICKAXE : 
		case IRON_PICKAXE : 
		case DIAMOND_PICKAXE : 
			
		case BOW :
		case FISHING_ROD :
		case SHEARS :
		case CARROT_STICK : return true;
		
		default: return false;
		}
	}
}
