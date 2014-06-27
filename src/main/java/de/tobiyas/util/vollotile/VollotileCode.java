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
package de.tobiyas.util.vollotile;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public abstract class VollotileCode {
	
	/**
	 * The relocation string for the Vollotile code.
	 */
	protected final String CB_RELOCATION;
	
	
	public VollotileCode(String relocationString) {
		this.CB_RELOCATION = relocationString;
	}
	
	/**
	 * Plays a Critical hit effect on the Player.
	 * 
	 * @param player to play on.
	 */
	public abstract void playCriticalHitEffect(Player toSendTo, Entity toPlayEffect);
	
	
	/**
	 * Plays a Critical hit effect on the Entity.
	 * Sent to all Players.
	 * 
	 * @param toPlayEffect the entity to show on.
	 */
	public void playCriticalHitEffect(Entity toPlayEffect){
		for(Player player : toPlayEffect.getWorld().getPlayers()){
			if(player.getLocation().distanceSquared(toPlayEffect.getLocation()) < 50 * 50){
				playCriticalHitEffect(player, toPlayEffect);
			}
		}
	}
	
	
	/**
	 * Sends a Raw Message to the player passed.
	 * 
	 * @param player to send
	 */
	public void sendRawMessage(Player player, String rawMessage){
		try{
			String seperator = ".";

			Object mcPlayer = ReflectionsHelper.getMCEntityFromBukkitEntity(player);
			Class<? extends Object> iChatBaseComponent = Class.forName("net" + seperator + "minecraft" + seperator + "server" + seperator 
					+ CB_RELOCATION + seperator + "IChatBaseComponent");
			
			
			Class<? extends Object> chatSerializer = Class.forName("net" + seperator + "minecraft" + seperator + "server" + seperator + 
					CB_RELOCATION + seperator + "ChatSerializer");
			
			Method serialize = chatSerializer.getMethod("a", String.class);
			Object serializedMessage = serialize.invoke(null, rawMessage);

			Method sendMessage = mcPlayer.getClass().getMethod("sendMessage", iChatBaseComponent);
			sendMessage.invoke(mcPlayer, serializedMessage);
		}catch(Throwable exp){
			player.sendMessage(rawMessage);
		}
	}
	
	
	
	/**
	 * Removes the Particle Effect off the Entity.
	 * 
	 * @param entity to remove the effect.
	 */
	public void removeParticleEffect(LivingEntity entity){
		Object mcPlayer = ReflectionsHelper.getMCEntityFromBukkitEntity(entity);
		try{
			Field dataWatcher = mcPlayer.getClass().getField("datawatcher");
			Method method = dataWatcher.getClass().getMethod("watch", Integer.class, Object.class);
			
			//Field updateEffects = mcPlayer.getClass().getField("updateEffects");
			
			method.invoke(dataWatcher, 8, (byte) 0);
			method.invoke(dataWatcher, 7, Integer.valueOf(0));
			
			//updateEffects.set(mcPlayer, false);
		}catch(Exception exp){
			//exp.printStackTrace();
			//didn't work.... but well who cares. :D
		}
	}
	
	
	
	/**
	 * Sends a packet to the player that contains a Particle effect
	 * 
	 * @param effect to send
	 * @param loc to send to
	 * @param player to send to
	 */
	public abstract void sendParticleEffect(ParticleEffects effect, Location loc, Vector width, float data, int amount, Player player);
	
	
	/**
	 * Sends a packet to the player that contains a Particle effect
	 * 
	 * @param effect to send
	 * @param loc to send to
	 * @param player to send to
	 */
	public void sendParticleEffectToAll(ParticleEffects effect, Location loc, float data, int amount){
		for(Player player : loc.getWorld().getPlayers()){
			sendParticleEffect(effect, loc, new Vector(1,1,1), data, amount, player);
		}
	}
	
	
	/**
	 * Checks if this version is the Correct Version for the Vollotile Code.
	 * 
	 * @return true if is the correct version, false if not.
	 */
	public boolean isCorrectVersion(){
		try{
			String seperator = ".";
			Class<?> clazz = Class.forName("net" + seperator + "minecraft" + seperator + "server" + seperator + CB_RELOCATION + seperator + "Entity");
			return clazz != null;
		}catch(ClassNotFoundException exp){
			return false;
		}
	}
	
	
	@Override
	public String toString(){
		return "Vollotile: " + CB_RELOCATION;
	}

	
	
}
