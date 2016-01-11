package de.tobiyas.util.vollotile.helper;

import java.util.Collection;
import java.util.Iterator;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import de.tobiyas.util.vollotile.ParticleEffects;
import de.tobiyas.util.vollotile.VollotileCodeManager;

public class ParticleHelper {

	/**
	 * A Vector Upwards.
	 */
	public static final Vector UP = new Vector(0,0.2,0);
	
	
	
	/**
	 * Sends a packet to the player that contains a Particle effect
	 * 
	 * @param effect to send
	 * @param loc to send to
	 * @param player to send to
	 */
	public static void sendParticleEffectToAll(ParticleEffects effect, Location loc, Vector width, float data, int amount){
		VollotileCodeManager.getVollotileCode().sendParticleEffectToAll(effect, loc, width, data, amount);
	}
	
	
	/**
	 * Sends a packet to the player that contains a Particle effect
	 * 
	 * @param effect to send
	 * @param loc to send to
	 * @param player to send to
	 */
	public static void sendXParticleEffectToAllWithRandWidth(ParticleEffects effect, Location loc, float data, int amount){
		//This is Better for performance, because the players are pre-checked!
		Collection<Player> players = loc.getWorld().getPlayers();
		Iterator<Player> it = players.iterator();
		
		while(it.hasNext()){
			if(it.next().getLocation().distanceSquared(loc) < 30 * 30) it.remove();
		}
		
		//Now send!
		for(Player player : players){
			for(int i = 0; i < amount; i++){
				VollotileCodeManager.getVollotileCode().sendParticleEffect(effect, loc, smallRandVec(), data, 1, player);
			}
		}
	}
	
	
	
	/**
	 * A Random vector in random direction.
	 * @return the vector in a random direction.
	 */
	public static Vector randVec(double max){
		double x = (Math.random() * max * 2) - max;
		double y = (Math.random() * max * 2) - max;
		double z = (Math.random() * max * 2) - max;
		
		return new Vector(x, y, z);
	}
	
	
	/**
	 * A Random vector in random direction.
	 * @return the vector in a random direction.
	 */
	public static Vector smallRandVec(){
		return randVec(0.4);
	}
	
	/**
	 * A Random vector in random direction.
	 * @return the vector in a random direction.
	 */
	public static Vector bigRandVec(){
		return randVec(2);
	}
	
	
}
