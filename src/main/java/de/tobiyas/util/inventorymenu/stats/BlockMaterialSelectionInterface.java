package de.tobiyas.util.inventorymenu.stats;

import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import de.tobiyas.util.inventorymenu.BasicSelectionInterface;

public class BlockMaterialSelectionInterface extends MaterialSelectionInterface {

	public BlockMaterialSelectionInterface(Player player,
			BasicSelectionInterface parent, Map<String, Object> config,
			String key, JavaPlugin plugin) {
		super(player, parent, config, key, plugin);
	}

	@Override
	protected boolean filter(ItemStack item) {
		return item.getType().isBlock();
	}
	
}
