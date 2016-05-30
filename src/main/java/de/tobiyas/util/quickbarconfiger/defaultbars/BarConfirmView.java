package de.tobiyas.util.quickbarconfiger.defaultbars;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.tobiyas.util.quickbarconfiger.Bar;
import de.tobiyas.util.quickbarconfiger.BarHandler;

public class BarConfirmView extends Bar {

	/**
	 * The Used callback
	 */
	private final YesNoCallback callBack;
	
	public BarConfirmView(BarHandler barHandler, Player player, YesNoCallback callback) {
		super(barHandler, player);
		
		this.callBack = callback;
	}


	@Override
	@SuppressWarnings("deprecation")
	protected void updateItemsIntern() {
		ItemStack itemYes = generateItem(Material.WOOL, DyeColor.GREEN.getWoolData(), callBack.getYesText());
		ItemStack itemNo = generateItem(Material.WOOL, DyeColor.RED.getWoolData(), callBack.getNoText());
		
		this.clickList.set(1, new ItemClickBi(itemYes, new ItemClickedCallback() { @Override public void itemClicked(boolean shift, Block block) { yes(block); } }));
		this.clickList.add(2, new ItemClickBi(itemNo, new ItemClickedCallback() { @Override public void itemClicked(boolean shift, Block block) { no(block); } }));
	}
	
	
	private void yes(Block unused){
		callBack.yesPressed();
		barHandler.backPressed();
	}
	
	private void no(Block unused){
		callBack.noPressed();
		barHandler.backPressed();
	}
	
	
	public static interface YesNoCallback {
		public String getYesText();
		public void yesPressed();
		
		public String getNoText();
		public void noPressed();
	}

}
