package de.tobiyas.util.quickbarconfiger.defaultbars;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffectType;

import de.tobiyas.util.quickbarconfiger.BarHandler;

public class BarPotionChooser extends AbstractMultiBar implements Listener {

	/**
	 * The Callback to use.
	 */
	private final PotionChooserCallback callback;
	
	/**
	 * The Old value.
	 */
	private final PotionEffectType oldValue;
	
	
	public BarPotionChooser(BarHandler barHandler, Player player, PotionEffectType oldValue, PotionChooserCallback callback) {
		super((int) Math.ceil(PotionEffectType.values().length / 6d), barHandler, player);
		
		this.oldValue = oldValue;
		this.callback = callback;
	}
	

	@Override
	protected void updateLayer(int currentLayer) {
		int start = currentLayer * 6;
		for(int i = 0; i < 6; i++){
			this.clickList.set(i+1, new PotionTypeEffectCallback(PotionEffectType.values()[start+i]));
		}
	}
	
	
	@Override
	protected void back(boolean shift, Block unused) {
		callback.newPotionChosen(true, oldValue, oldValue);
		super.back(shift, unused);
	}
	
	
	private class PotionTypeEffectCallback extends ItemClickBi {
		public PotionTypeEffectCallback(final PotionEffectType type) {
			super(
				generateItem(Material.POTION, (short)0, "Typ: " + (type == null ? "KEINE" : type.getName()), "Ändert den Typen auf diesen."),
				new ItemClickedCallback() {
					@Override
					public void itemClicked(boolean shift, Block block) {
						callback.newPotionChosen(false, oldValue, type);
						BarPotionChooser.this.barHandler.backPressed();
					}
				});
		}
	}
	
	
	
	public static interface PotionChooserCallback{
		/** A new Effect was chosen */
		void newPotionChosen(boolean cancled, PotionEffectType oldValue, PotionEffectType newValue);
	}

}
