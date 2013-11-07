package de.tobiyas.util.inventorymenu.stats;

import java.util.LinkedList;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import de.tobiyas.util.inventorymenu.BasicSelectionInterface;
import de.tobiyas.util.inventorymenu.elements.ScrollableItems;

public class MaterialSelectionInterface extends AbstractStatSelectionInterface {

	/**
	 * The ItemScrollView containing all Item Materials.
	 */
	protected ScrollableItems itemScrollView;
	
	/**
	 * The Indicator which Material is selected.
	 */
	protected ItemStack indicatorItem;
	
	/**
	 * The currently selected Material.
	 */
	protected Material selection = null;
	
	
	public MaterialSelectionInterface(Player player,
			BasicSelectionInterface parent,
			Map<String, Object> config, String key, JavaPlugin plugin) {
	
		super(player, parent, "Select a Material", config, key, plugin);
		
		itemScrollView = new ScrollableItems(selectionInventory, 0, 8, 0);
		selection = Material.ARROW;
		if(config.containsKey(key)){
			selection = (Material) config.get(key);
		}
		
		redraw();
		
	}
	
	/**
	 * Redraws the View.
	 */
	protected void redraw(){
		itemScrollView.clear();
		
		for(Material mat : Material.values()){
			ItemStack item = new ItemStack(mat);
			if(filter(item)){
				itemScrollView.addItem(item);
			}
		}
		
		redrawIndicatorItem();
	}
	
	/**
	 * Redraws the Indicator to the current Selection.
	 */
	private void redrawIndicatorItem() {
		indicatorItem = generateItem(selection, ChatColor.AQUA +
				"Current Material", new LinkedList<String>());
		controlInventory.setItem(4, indicatorItem);
	}
	

	/**
	 * For Filter Interfaces.
	 * Filter Interfaces can override this and filter Items.
	 * 
	 * @param item to filter
	 * @return true if it should be shown, false otherwise
	 */
	protected boolean filter(ItemStack item){
		return true;
	}
	
	@Override
	protected Object unparseValue() {
		return selection;
	}

	@Override
	protected void onSelectionItemPressed(ItemStack item) {
		if(itemScrollView.checkScrollButtons(item)) return;
		
		for(ItemStack ownItem : itemScrollView.getAllItems()){
			if(item.equals(ownItem)){
				selection = item.getType();
				
				redrawIndicatorItem();
				return;
			}
		}
	}


	@Override
	protected void onControlItemPressed(ItemStack item) {
		if(indicatorItem.equals(item)){
			//No need for a callback of the Indicator.
			return;
		}
	}

}
