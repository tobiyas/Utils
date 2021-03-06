/*******************************************************************************
 * Copyright 2014 Tobias Welther
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.tobiyas.util.inventorymenu.elements;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import de.tobiyas.util.vollotile.VollotileCode.MCVersion;
import de.tobiyas.util.vollotile.VollotileCodeManager;

public class ScrollableItems {

	/**
	 * This is the button to 
	 */
	private ItemStack upButton;
	
	/**
	 * The Item representing the down button
	 */
	private ItemStack downButton;
	
	
	/**
	 * The Inventory to spawn
	 */
	private final Inventory inventory;
	
	/**
	 * The position from where to create.
	 * Min: 0
	 */
	private final int fromColumn;
	
	/**
	 * The position to where to create
	 * maximum: 8
	 */
	private final int toColumn;
	
	
	/**
	 * This is the list to scroll through
	 */
	private final List<ItemStack> itemsToScroll;
	
	/**
	 * The List of all Visible Items
	 */
	private final List<ItemStack> visibleItems;

	/**
	 * This indicates the current Row where we are at the moment
	 */
	private int currentRow = 0;
	
	
	/**
	 * The min row to write to.
	 */
	private final int fromRow;
	
	/**
	 * If the Items should sort for names or not
	 */
	private boolean sortForNames = false;
	
	
	/**
	 * Creates a fullscreen (full inventory) view.
	 * 
	 * @param inventory to create to.
	 */
	public ScrollableItems(Inventory inventory){
		this(inventory, 0, 8, 0);
	}
	
	
	public ScrollableItems(Inventory inventory, int fromColumn, int toColumn, int fromRow) {
		this.inventory = inventory;
		this.fromColumn = fromColumn;
		this.toColumn = toColumn;
		this.fromRow = fromRow;
		
		visibleItems = new LinkedList<ItemStack>();
		
		itemsToScroll = new LinkedList<ItemStack>();

		upButton = generateUpItem();
		downButton = generateDownItem();
		
		
		redrawCurrent();
	}
	
	
	
	/**
	 * Generates the Down-Item.
	 * 
	 * @return the down Item.
	 */
	private ItemStack generateDownItem(){
		if(hasInventoryHeadSupport()) return generateHead("MHF_ArrowDown", ChatColor.RED + "Scroll Down", "Scrolls the List down.");
		
		ItemStack item = new ItemStack(Material.BLAZE_ROD);
		ItemMeta meta = downButton.getItemMeta();
		List<String> lore = new LinkedList<String>();
		lore.add("LESS");
		meta.setLore(lore);
		meta.setDisplayName(ChatColor.RED + "Scroll Down");
		item.setItemMeta(meta);
		
		return item;
	}
	
	/**
	 * Generates a Head for the playername passed.
	 * 
	 * @param playerHeadName to generate
	 * @param displayname to set
	 * @param lore to set
	 * 
	 * @return the generated Head.
	 */
	private ItemStack generateHead(String playerHeadName, String displayname, String... lore) {
		ItemStack head = new ItemStack(Material.SKULL_ITEM);
		head.setDurability((short) 3);
		SkullMeta meta = (SkullMeta) head.getItemMeta();
		
		meta.setOwner(playerHeadName);
		meta.setLore(Arrays.asList(lore));
		meta.setDisplayName(displayname);
		
		head.setItemMeta(meta);
		return head;
	}

	
	private ItemStack generateUpItem(){
		if(hasInventoryHeadSupport()) return generateHead("MHF_ArrowUp", ChatColor.RED + "Scroll Up", "Scrolls the List down.");
		
		ItemStack item = new ItemStack(Material.ARROW);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = new LinkedList<String>();
		lore.add("MORE");
		meta.setLore(lore);
		meta.setDisplayName(ChatColor.GREEN + "Scroll Up");
		item.setItemMeta(meta);
		//MHF_ArrowUp
		return item;
	}
	
	
	
	/**
	 * If inventory heads are resolved.
	 * 
	 * @return true if has support.
	 */
	private boolean hasInventoryHeadSupport(){
		return VollotileCodeManager.getVollotileCode().getVersion().isVersionGreaterOrEqual(MCVersion.v1_8_R1);
	}
	
	
	
	/**
	 * Set's the up button.
	 * 
	 * @param item to set to
	 */
	public void setUpButton(ItemStack item){
		upButton = item.clone();
		upButton.setAmount(1);
		redrawCurrent();
	}
	
	public void setDownButton(ItemStack item){
		upButton = item.clone();
		upButton.setAmount(1);
		redrawCurrent();
	}
	
	
	/**
	 * Redraws the current configuration of the view
	 */
	private void redrawCurrent(){
		if(sortForNames) sortItems();
		
		int height = inventory.getSize() / 9;
		int width = (toColumn - fromColumn + 1) - 1;
		if(width <= 1) return;
		
		int totalRows = (int) Math.ceil((double)itemsToScroll.size() / (double)width);
		
		boolean showUpButton = currentRow != 0;
		boolean showDownButton = totalRows > (currentRow + height);
		
		int startIndex = (currentRow * 9) + fromColumn - 1;
		if(startIndex < 0) startIndex = 0;

		visibleItems.clear();
		
		int index = width * currentRow;
		for(int i_height = fromRow; i_height < height; i_height++){
			for(int i_width = fromColumn; i_width < width; i_width++){
				inventory.setItem((i_height * 9) + i_width,  null);
				if(index >= itemsToScroll.size()) continue;
				
				ItemStack item = itemsToScroll.get(index);
				inventory.setItem((i_height * 9) + i_width,  item);
				visibleItems.add(item);
				
				index++;
			}
			
		}
		
		inventory.setItem((9 * fromRow) + toColumn, null);
		inventory.setItem((9 * (fromRow + 2)) + toColumn, null);
		if(showUpButton){
			upButton.setAmount(currentRow);
			inventory.setItem((9 * fromRow) + toColumn, upButton);
		}

		if(showDownButton){
			int amount = totalRows - currentRow - height;
			if(amount <= 0) amount = 1;
			
			downButton.setAmount(amount);
			inventory.setItem((9 * (fromRow + 2)) + toColumn, downButton);
		}
	}
	
	/**
	 * Resorts the List of items to names.
	 * <br> WARNING EXPERIMENTAL.
	 */
	private void sortItems() {
		Map<String, ItemStack> assoc = new HashMap<String, ItemStack>();
		List<String> list = new LinkedList<String>();
		for(ItemStack item : itemsToScroll){
			String name = item.getType().name();
			if(item.getItemMeta().hasDisplayName()){
				name = ChatColor.stripColor(item.getItemMeta().getDisplayName());
			}
			
			list.add(name);
			assoc.put(name, item);
		}
		Collections.sort(list);
		
		int i = 0;
		for(String name : list){
			ItemStack item = assoc.get(name);
			itemsToScroll.set(i, item);
			i++;
		}
	}


	/**
	 * Adds an item to the Scrollable Items
	 * 
	 * @param item
	 */
	public void addItem(ItemStack item){
		this.itemsToScroll.add(item);
		redrawCurrent();
	}
	
	
	/**
	 * Adds all the items.
	 * 
	 * @param items
	 */
	public void addItems(Collection<ItemStack> items){
		this.itemsToScroll.addAll(items);
		redrawCurrent();
	}
	
	
	/**
	 * Removed an Item to from the Scrollable Items
	 * 
	 * @param item
	 */
	public void removeItem(ItemStack item){
		this.itemsToScroll.remove(item);
		redrawCurrent();
	}
	
	/**
	 * Returns all Items
	 * 
	 * @return
	 */
	public List<ItemStack> getAllItems(){
		return itemsToScroll;
	}
	
	
	/**
	 * Checks if a Scroll button has been pressed.
	 * 
	 * @param item
	 * @return
	 */
	public boolean checkScrollButtons(ItemStack item){
		if(upButton.equals(item)){
			scrollUp();
			return true;
		}

		if(downButton.equals(item)){
			scrollDown();
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * Scrolls the view 1 row down
	 */
	public void scrollDown(){
		int width = (toColumn - fromColumn + 1) - 1;		
		int totalRows = (int) Math.ceil((double)itemsToScroll.size() / (double)width);
		int height = inventory.getSize() / 9;
		currentRow ++;

		if((currentRow + height) > totalRows) currentRow --;

		redrawCurrent();
	}
	
	
	/**
	 * Scrolls the view 1 up
	 */
	public void scrollUp(){
		currentRow --;
		if(currentRow < 0) currentRow = 0;
		
		redrawCurrent();
	}

	/**
	 * Clears the Inventory of the Scrollable Items.
	 * This means it also clears it's rows.
	 */
	public void clear() {
		currentRow = 0;
		itemsToScroll.clear();
		visibleItems.clear();
		
		redrawCurrent();
	}


	public boolean isSortForNames() {
		return sortForNames;
	}


	public void setSortForNames(boolean sortForNames) {
		this.sortForNames = sortForNames;
		redrawCurrent();
	}


	/**
	 * Redraws the View.
	 */
	public void redraw() {
		redrawCurrent();
	}
	
	
	/**
	 * Sorts the items to scroll.
	 * 
	 * @param sorter that sorts the items.
	 */
	public void sortBySorter(ItemSorter sorter){
		List<ItemStack> sorted = sorter.sortItems(itemsToScroll);
		itemsToScroll.clear();
		itemsToScroll.addAll(sorted);
		
		redraw();
	}


	/**
	 * Starts a sort by name sort.
	 */
	public void sortByNameNow() {
		sortItems();
	}

}
