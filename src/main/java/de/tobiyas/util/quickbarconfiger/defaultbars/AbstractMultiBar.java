package de.tobiyas.util.quickbarconfiger.defaultbars;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import de.tobiyas.util.quickbarconfiger.Bar;
import de.tobiyas.util.quickbarconfiger.BarHandler;

public abstract class AbstractMultiBar extends Bar {

	/**
	 * The max amount of bars.
	 */
	protected int maxBars;
	
	/**
	 * The current Bar number
	 */
	protected int barNr = 0;
	
	
	
	public AbstractMultiBar(int maxBars, BarHandler barHandler, Player player) {
		super(barHandler, player);
		
		this.maxBars = maxBars;
	}
	
	
	
	@Override
	protected final void updateItemsIntern() {
		//First update the layer.
		updateLayer(barNr);
		
		//Set back items:
		if(barNr > 0){
			clickList.set(0, new ItemClickBi(
				generateItem(Material.FLINT, (short)0, ChatColor.YELLOW+"Zurück", ChatColor.AQUA+"Gehe zu Ebene " + barNr),
				new ItemClickedCallback(){@Override public void itemClicked(boolean shift, Block block) { layerBackClicked(); }}));
		}
		
		//Set for items:
		if(barNr < maxBars-1){
			clickList.set(7, new ItemClickBi(
				generateItem(Material.FLINT, (short)0, ChatColor.YELLOW+"Weiter", ChatColor.AQUA+"Gehe zu Ebene " + (barNr+2)),
				new ItemClickedCallback(){@Override public void itemClicked(boolean shift, Block block) { layerForClicked(); }}));
		}
	}
	
	/**
	 * Update the layer for the clickList.
	 * <br>To add, use: clickList#set(position,ItemClickBi)
	 * @param currentLayer to use.
	 */
	protected abstract void updateLayer(int currentLayer);


	/**
	 * Clicked on the Layer back.
	 */
	protected void layerBackClicked() {
		this.barNr = Math.max(0, this.barNr-1);
		this.redraw();
	}
	
	/**
	 * Clicked on the Layer for.
	 */
	protected void layerForClicked() {
		this.barNr = Math.min(maxBars-1, barNr+1);
		this.redraw();
	}

}
