package de.tobiyas.util.inventorymenu.elements;

import java.util.List;

import org.bukkit.inventory.ItemStack;

public interface ItemSorter {

	
	/**
	 * Sorts the list of items passed.
	 * 
	 * @param itemsToSort the items to sort
	 * 
	 * @return the sorted list of items.
	 */
	public List<ItemStack> sortItems(List<ItemStack> itemsToSort);
}
