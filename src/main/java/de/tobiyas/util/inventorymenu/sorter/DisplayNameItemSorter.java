package de.tobiyas.util.inventorymenu.sorter;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import de.tobiyas.util.inventorymenu.elements.ItemSorter;

public class DisplayNameItemSorter implements ItemSorter {

	@Override
	public List<ItemStack> sortItems(List<ItemStack> itemsToSort) {
		List<ItemStack> unsorted = new LinkedList<ItemStack>(itemsToSort);
		List<ItemStack> sorted = new LinkedList<ItemStack>();
		
		while(!unsorted.isEmpty()){
			ItemStack lowest = null;
			
			for(ItemStack item : unsorted){
				if(isLower(lowest, item)) lowest = item;
			}
			
			//some error.
			if(lowest == null) break;
			
			unsorted.remove(lowest);
			sorted.add(lowest);
		}
		
		return sorted;
	}
	
	
	private boolean isLower(ItemStack before, ItemStack toCheck){
		if(before == null) return true;
		if(toCheck == null) return false;
		
		String beforeName = before.hasItemMeta() ? 
			before.getItemMeta().hasDisplayName() ? 
				before.getItemMeta().getDisplayName()
					: before.getType().name()
					: before.getType().name();

		String toCheckName = toCheck.hasItemMeta() ? 
				toCheck.getItemMeta().hasDisplayName() ? 
						toCheck.getItemMeta().getDisplayName()
					: toCheck.getType().name()
					: toCheck.getType().name();
										
		return isLower(beforeName, toCheckName);
	}
	
	
	/**
	 * If the String is before or not.
	 * 
	 * @param before
	 * @param after
	 * 
	 * @return
	 */
	private boolean isLower(String before, String toCheck){
		return before.compareTo(toCheck) > 0;
	}

}
