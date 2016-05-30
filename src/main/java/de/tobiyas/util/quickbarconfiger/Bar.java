package de.tobiyas.util.quickbarconfiger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tobiyas.util.collections.Bi;

public abstract class Bar {

	/**
	 * The Back item.
	 */
	@SuppressWarnings("deprecation")
	protected final ItemStack itemBack = generateItem(Material.WOOL, DyeColor.RED.getWoolData(), ChatColor.RED+"Zurück", "Bringt dich zurück");	
	
	/**
	 * The Exit item.
	 */
	@SuppressWarnings("deprecation")
	protected final ItemStack itemExit = generateItem(Material.WOOL, DyeColor.RED.getWoolData(), ChatColor.RED+"Schließen", "Schließt die Bar.");
	
	
	/**
	 * The Used items.
	 */
	protected final List<ItemClickBi> clickList = new ArrayList<ItemClickBi>(9);
	
	/**
	 * The way to use.
	 */
	protected final Player player;
	
	/**
	 * The BarHandler to use.
	 */
	protected final BarHandler barHandler;
	
	
	
	public Bar(BarHandler barHandler, Player player) {
		this.barHandler = barHandler;
		this.player = player;
		
		//Fill bar to have 9 items!
		clearItems();
	}
	
	
	
	/**
	 * The Item was pressed on that block.
	 * @param item to use.
	 * @param block to use.
	 */
	public void slotPressed(int slot, Block block){
		boolean shift = player.isSneaking();
		
		ItemClickBi bi = clickList.get(slot);
		if(bi != null) bi.getValue().itemClicked(shift, block);
	}
	
	/**
	 * Update items to current Stuff.
	 */
	public final void updateItems(){
		clearItems();
		updateItemsIntern();
		
		clickList.set(8, new ItemClickBi(barHandler.isRootView(this) ? itemExit : itemBack, new ItemClickedCallback() {
			@Override public void itemClicked(boolean shift, Block block) { back(shift, block); }
		}));
	}
	
	
	/**
	 * Go back to last layer.
	 */
	protected void back(boolean shift, Block unused){
		barHandler.backPressed();
	}
	
	/**
	 * This is called when the gui is force closed.
	 */
	protected void forceClose(){}
	
	
	/**
	 * Clears all current Items.
	 */
	protected void clearItems(){
		clickList.clear();
		for(int i = 0; i < 9; i++) clickList.add(new EmptyItemBi());
	}
	
	
	/**
	 * Update items to current Stuff.
	 * <br> Slot 8 is ALWAYS overwritten with Back!
	 */
	protected abstract void updateItemsIntern();
	
	
	/**
	 * Gets the item to show.
	 */
	public List<ItemStack> getItemsToShow(){
		List<ItemStack> items = new ArrayList<ItemStack>(9);
		for(ItemClickBi bi : clickList) items.add(bi.getKey());
		return items;
	}
	
	
	/**
	 * Forces the BarHandler to redraw.
	 */
	protected void redraw(){
		this.barHandler.redrawCurrentBar();
	}
	
	
	public static class ItemClickBi extends Bi<ItemStack,ItemClickedCallback>{
		public ItemClickBi(ItemStack val1, ItemClickedCallback val2) {
			super(val1, val2);
		}
	}

	public static class ItemShowBi extends ItemClickBi{
		public ItemShowBi(ItemStack val1) {
			super(val1, new ItemClickedCallback() {
				@Override public void itemClicked(boolean shift, Block block) {}
			});
		}
	}
	
	public static class EmptyItemBi extends ItemShowBi{
		public EmptyItemBi() {
			super(null);
		}
	}
	
	
	public static interface ItemClickedCallback{
		void itemClicked(boolean shift, Block block);
	}

	protected static ItemStack generateItem(Material material, short damageValue, String name, String... lore){
		ItemStack item = new ItemStack(material, 1, damageValue);
		ItemMeta meta = item.getItemMeta();
		if(name != null && !name.isEmpty()) meta.setDisplayName(name);
		if(lore.length > 0) meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		
		return item;
	}
	
}
