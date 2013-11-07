package de.tobiyas.util.inventorymenu.stats;

import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import de.tobiyas.util.inventorymenu.BasicSelectionInterface;

public class ArmorMaterialSelectionInterface extends MaterialSelectionInterface {

	public ArmorMaterialSelectionInterface(Player player,
			BasicSelectionInterface parent, Map<String, Object> config,
			String key, JavaPlugin plugin) {
		super(player, parent, config, key, plugin);
	}

	@Override
	protected boolean filter(ItemStack item) {
		switch(item.getType()){
			case LEATHER_HELMET:
			case LEATHER_CHESTPLATE:
			case LEATHER_LEGGINGS:
			case LEATHER_BOOTS:

			case CHAINMAIL_HELMET:
			case CHAINMAIL_CHESTPLATE:
			case CHAINMAIL_LEGGINGS:
			case CHAINMAIL_BOOTS:
				
			case GOLD_HELMET:
			case GOLD_CHESTPLATE:
			case GOLD_LEGGINGS:
			case GOLD_BOOTS:
			
			case IRON_HELMET:
			case IRON_CHESTPLATE:
			case IRON_LEGGINGS:
			case IRON_BOOTS:

			case DIAMOND_HELMET:
			case DIAMOND_CHESTPLATE:
			case DIAMOND_LEGGINGS:
			case DIAMOND_BOOTS: return true;
		
			default: return false;
		}
	}
}
