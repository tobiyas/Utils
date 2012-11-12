package de.tobiyas.util.config.returncontainer;

import org.bukkit.inventory.ItemStack;

public class DropContainer {

	private ItemStack item;
	private int max;
	private int min;
	private double probability;
	
	public DropContainer(ItemStack item, int min, int max, double probability){
		this.item = item;
		this.min = min;
		this.max = max;
		this.probability = probability;
	}

	//Getter
	public ItemStack getItem() {
		return item;
	}

	public int getMax() {
		return max;
	}

	public int getMin() {
		return min;
	}

	public double getProbability() {
		return probability;
	}
}
