package de.tobiyas.util.quickbarconfiger.defaultbars;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tobiyas.util.quickbarconfiger.Bar;
import de.tobiyas.util.quickbarconfiger.BarHandler;

public class BarMaterialChooser extends Bar {
	
	private static final List<Material> canNotBeShown = Arrays.asList(
			Material.WATER, Material.STATIONARY_WATER,
			Material.LAVA, Material.STATIONARY_LAVA,
			Material.BED_BLOCK, Material.PISTON_EXTENSION, Material.PISTON_MOVING_PIECE,
			Material.DOUBLE_STEP, Material.IRON_DOOR_BLOCK, Material.REDSTONE_WIRE,
			Material.CROPS, Material.SOIL, Material.BURNING_FURNACE, Material.FIRE,
			Material.SIGN_POST, Material.WOODEN_DOOR
		); 

	/**
	 * The Material to choose from
	 */
	private final List<Material> toChooseFrom;
	
	/**
	 * The Callback to use.
	 */
	private final MaterialChangedCallback callback;
	
	/**
	 * The StartSlot.
	 */
	private int startSlot = 0;
	
	
	public BarMaterialChooser(BarHandler barHandler, Player player, MaterialChangedCallback callback, List<Material> toChooseFrom) {
		super(barHandler, player);
		
		this.callback = callback;
		this.toChooseFrom = toChooseFrom;
	}
	

	@Override
	protected void updateItemsIntern() {
		ItemStack itemPrev = generateItem(Material.ARROW, (short)0, ChatColor.GREEN + "Links", "Rotiere nach Links");
		ItemStack itemNext = generateItem(Material.ARROW, (short)0, ChatColor.GREEN + "Rechts", "Rotiere nach Rechts");
		
		//Display 6 items if there are 6:
		for(int i = 0; i < 6; i++){
			int index = startSlot+i;
			if(index >= toChooseFrom.size()) continue;
			this.clickList.set(i+1, new MaterialClickedCallback(toChooseFrom.get(index)));
		}
		
		
		if(startSlot != 0) this.clickList.set(0, new ItemClickBi(itemPrev, new ItemClickedCallback() { @Override public void itemClicked(Block block) {prev();} }));
		if(startSlot+6 <= toChooseFrom.size()) this.clickList.set(7, new ItemClickBi(itemNext, new ItemClickedCallback() { @Override public void itemClicked(Block block) {next();} }));
	}
	
	
	private void prev(){
		this.startSlot = Math.max(0, startSlot-1);
	}
	
	private void next(){
		this.startSlot = Math.max(0, startSlot-1);
	}
	
	
	
	
	private class MatCallback implements ItemClickedCallback {
		private final Material mat;
		
		public MatCallback(Material mat) {
			this.mat = mat;
		}
		
		@Override
		public void itemClicked(Block block) {
			callback.materialChanged(mat);
			back(null);
		}
	}
	
	private class MaterialClickedCallback extends ItemClickBi {
		public MaterialClickedCallback(Material mat) {
			super(generateItem(mat), new MatCallback(mat));
		}
	}

	private static ItemStack generateItem(Material mat){
		Material displayMat = canNotBeShown.contains(mat) ? Material.BARRIER : mat;
		
		ItemStack item = new ItemStack(displayMat);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.AQUA + mat.name());
		
		item.setItemMeta(meta);
		return item;
	}
	
	public static interface MaterialChangedCallback{
		public void materialChanged(Material material);
	}
	
}
