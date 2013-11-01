package de.tobiyas.util.inventorymenu.stats;

import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.tobiyas.util.inventorymenu.BasicSelectionInterface;

public class StatsSelectionInterfaceFactory {

	
	public static AbstractStatSelectionInterface buildInterface(StatType type, Player player, BasicSelectionInterface parent,
			Map<String, Object> config, String key, JavaPlugin plugin){
		
		switch(type){
			case BOOLEAN: return new BooleanSelectionInterface(player, parent, config, key, plugin);
			case DOUBLE: return new DoubleSelectionInterface(player, parent, config, key, plugin);
			case INTEGER: return new IntegerSelectionInterface(player, parent, config, key, plugin);
			case OPERATOR: return new OperatorSelectionInterface(player, parent, config, key, plugin);
			case STRING: return new StringSelectionInterface(player, parent, config, key, plugin);
		}
		
		return null;
	}
}
