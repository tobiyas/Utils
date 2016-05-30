package de.tobiyas.util.quickbarconfiger.defaultbars;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.tobiyas.util.quickbarconfiger.Bar;
import de.tobiyas.util.quickbarconfiger.BarHandler;

public class BarIntChoose extends Bar {

	/**
	 * The Current value.
	 */
	private int current;
	
	/**
	 * THe Callback to call when done.
	 */
	private final IntSelectedCallback callback;
	
	
	public BarIntChoose(BarHandler barHandler, Player player, int oldValue, IntSelectedCallback callback) {
		super(barHandler, player);
		
		this.current = oldValue;
		this.callback = callback;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void updateItemsIntern() {
		ItemStack itemAccept = generateItem(Material.WOOL, DyeColor.GREEN.getWoolData(), ChatColor.GREEN + "Dieser!", ChatColor.GREEN + "Nimmt diesen WP.", ChatColor.GREEN+"Der nächste wird danach eingefügt.");
		ItemStack item_10000s = generateItem(Material.WOOL, DyeColor.CYAN.getWoolData(), ChatColor.GREEN+"10000-er");
		ItemStack item_1000s = generateItem(Material.WOOL, DyeColor.CYAN.getWoolData(), ChatColor.GREEN+"1000-er");
		ItemStack item_100s = generateItem(Material.WOOL, DyeColor.CYAN.getWoolData(), ChatColor.GREEN+"100-er");
		ItemStack item_10s = generateItem(Material.WOOL, DyeColor.CYAN.getWoolData(), ChatColor.GREEN+"10-er");
		ItemStack item_1s = generateItem(Material.WOOL, DyeColor.CYAN.getWoolData(), ChatColor.GREEN+"1-er");
		
		//Apply amount:
		setAmount(item_1s, current % 10);
		setAmount(item_10s, (current / 10) % 10);
		setAmount(item_100s, (current / 100) % 10);
		setAmount(item_1000s, (current / 1000) % 10);
		setAmount(item_10000s, (current / 10000) % 10);
		
		this.clickList.set(0, new ItemClickBi(itemAccept, new ItemClickedCallback() { @Override public void itemClicked(boolean shift, Block block) { accept(); } }));

		this.clickList.set(6, new ItemClickBi(item_1s, new ItemClickedCallback() { @Override public void itemClicked(boolean shift, Block block) { if(!shift) increase_1s(); else decrease_1s(); } }));
		this.clickList.set(5, new ItemClickBi(item_10s, new ItemClickedCallback() { @Override public void itemClicked(boolean shift, Block block) { if(!shift) increase_10s(); else decrease_10s(); } }));
		this.clickList.set(4, new ItemClickBi(item_100s, new ItemClickedCallback() { @Override public void itemClicked(boolean shift, Block block) { if(!shift) increase_100s(); else decrease_100s(); } }));
		this.clickList.set(3, new ItemClickBi(item_1000s, new ItemClickedCallback() { @Override public void itemClicked(boolean shift, Block block) { if(!shift) increase_1000s(); else decrease_1000s(); } }));
		this.clickList.set(2, new ItemClickBi(item_10000s, new ItemClickedCallback() { @Override public void itemClicked(boolean shift, Block block) { if(!shift) increase_10000s(); else decrease_10000s(); } }));
	}
	
	
	private void setAmount(ItemStack item, int amount) {
		if(amount > 0) { item.setAmount(amount); return; }
		
		//If amount <= 0:
		item.setType(Material.BARRIER);
		item.setAmount(1);
		item.setDurability((short)0);
	}

	private void increase_1s(){
		current += 1;
		this.redraw();
	}
	
	private void decrease_1s(){
		current -= 1;
		this.redraw();
	}
	
	private void increase_10s(){
		current += 10;
		this.redraw();
	}
	
	private void decrease_10s(){
		current -= 10;
		this.redraw();
	}
	
	private void increase_100s(){
		current += 100;
		this.redraw();
	}
	
	private void decrease_100s(){
		current -= 100;
		this.redraw();
	}
	
	private void increase_1000s(){
		current += 1000;
		this.redraw();
	}
	
	private void decrease_1000s(){
		current -= 1000;
		this.redraw();
	}
	
	private void increase_10000s(){
		current += 10000;
		this.redraw();
	}

	private void decrease_10000s(){
		current -= 10000;
		this.redraw();
	}


	
	private void accept(){
		callback.selectedInt(current);
		barHandler.backPressed();
	}
	
	
	public static interface IntSelectedCallback{
		public void selectedInt(int selected);
	}
	
}
