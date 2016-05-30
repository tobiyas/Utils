package de.tobiyas.util.quickbarconfiger.defaultbars;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import de.tobiyas.util.quickbarconfiger.BarHandler;

public class BarEntityTypeChooser extends AbstractMultiBar implements Listener {

	/**
	 * The Callback to use.
	 */
	private final EntityTypeChooserCallback callback;
	
	/**
	 * The Old value.
	 */
	private final EntityType oldValue;
	
	
	public BarEntityTypeChooser(BarHandler barHandler, Player player, EntityType oldValue, EntityTypeChooserCallback callback) {
		super((int) Math.ceil(EntityType.values().length / 6d), barHandler, player);
		
		this.oldValue = oldValue;
		this.callback = callback;
	}
	

	@Override
	protected void updateLayer(int currentLayer) {
		int start = currentLayer * 6;
		for(int i = 0; i < 6; i++){
			this.clickList.set(i+1, new EntityTypeCallback(EntityType.values()[start+i]));
		}
	}
	
	
	@Override
	protected void back(boolean shift, Block unused) {
		callback.newEntityTypeChosen(true, oldValue, oldValue);
		super.back(shift, unused);
	}
	
	
	private class EntityTypeCallback extends ItemClickBi {
		@SuppressWarnings("deprecation")
		public EntityTypeCallback(final EntityType type) {
			super(
				generateItem(Material.ANVIL, (short)0, "Typ: " + type.getName(), "Ändert den Typen auf diesen."),
				new ItemClickedCallback() {
					@Override
					public void itemClicked(boolean shift, Block block) {
						callback.newEntityTypeChosen(false, oldValue, type);
					}
				});
		}
	}
	
	
	
	public static interface EntityTypeChooserCallback{
		/** A new Type was chosen */
		void newEntityTypeChosen(boolean cancled, EntityType oldValue, EntityType newValue);
	}

}
