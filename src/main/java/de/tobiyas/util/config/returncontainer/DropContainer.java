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
package de.tobiyas.util.config.returncontainer;

import java.util.Random;

import org.bukkit.inventory.ItemStack;
//import net.minecraft.server.v1_6_R3.NBTTagCompound;

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
	
	@SuppressWarnings("deprecation")
	public DropContainer(int itemId, int min, int max, double probability){
		this.item = new ItemStack(itemId);
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
	
	public ItemStack generateItem(){
		int amount = min + calcAbs();
		ItemStack stack = item.clone();
		stack.setAmount(amount);
		
		return item;
	}
	
	private int calcAbs(){
		Random rand = new Random();
		int total = 0;
		
		int abs = max - min;
		for(int i = 0; i < abs; i++)
			if(rand.nextDouble() < probability)
				total++;
			
		return total;	
	}
	
	/*
	public NBTTagCompound generateNBTTag(){
		NBTTagCompound compound = new NBTTagCompound();
		compound.setInt("max", max);
		compound.setInt("min", min);
		compound.setDouble("probability", probability);
		
		//TODO add item
		return compound;
	}
	
	public static DropContainer generateFromNBTTag(NBTTagCompound component){
		int max = component.getInt("max");
		int min = component.getInt("min");
		double probability = component.getDouble("probability");
		
		return new DropContainer(Material.APPLE.getId(), min, max, probability);
	}
	*/
}
