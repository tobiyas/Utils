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
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import de.tobiyas.util.UtilsUsingPlugin;
import de.tobiyas.util.chat.components.TellRawChatMessage;
import de.tobiyas.util.player.PlayerUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

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
	 * Sends a custom payload to the player.
	 * 
	 * @param player to send to
	 * @param channel to send to
	 * @param message to send.
	 */
	public void sendCustomPayload(Player player, String channel, String message) {
		ByteBuf buffer = Unpooled.buffer();
		buffer.writeBytes(message.getBytes(Charset.forName("UTF-8")));
		sendCustomPayload(player, channel, buffer);
	}
	

	/**
	 * Sends a custom Payload to the Player by Byte buffer:
	 * @param player to send to 
	 * @param channel to send to 
	 * @param buffer to use.
	 */
	public abstract void sendCustomPayload(Player player, String channel, ByteBuf buffer);
	
	
	
	/**
	 * Sends a packet to the player that contains a Particle effect
	 * 
	 * @param effect to send
	 * @param loc to send to
	 * @param player to send to
	 */
	public abstract void sendParticleEffect(ParticleEffects effect, Location loc, Vector width, float speed, int amount, Player player);
	
	
	/**
	 * Sets the header + Footer of the Tab list of the Player.
	 * @param player to set.
	 * @param header to set
	 * @param footer to set.
	 */
	public void setTabHeaderFooter(Player player, String header, String footer){ /* Empty Impl */ };
	
	
	/**
	 * Sends a packet to the player that contains a Particle effect
	 * 
	 * @param effect to send
	 * @param loc to send to
	 * @param player to send to
	 */
	public void sendParticleEffectToAll(ParticleEffects effect, Location loc, float data, int amount){
		sendParticleEffectToAll(effect, loc, new Vector(1,1,1), data, amount);
	}
	
	
	/**
	 * The Max particle Display range
	 */
	private final int maxRange = 64 * 64;
	
	/**
	 * Sends a packet to the player that contains a Particle effect
	 * 
	 * @param effect to send
	 * @param loc to send to
	 * @param player to send to
	 */
	public void sendParticleEffectToAll(ParticleEffects effect, Location loc, Vector width, float data, int amount){
		sendParticleEffectToPlayers(effect, loc, width, data, amount, PlayerUtils.getOnlinePlayers());
	}
	
	
	/**
	 * Sends a packet to the player that contains a Particle effect
	 * 
	 * @param effect to send
	 * @param loc to send to
	 * @param player to send to
	 */
	public void sendParticleEffectToPlayers(ParticleEffects effect, Location loc, Vector width, float data, int amount, Player... players){
		sendParticleEffectToPlayers(effect, loc, width, data, amount, Arrays.asList(players));
	}
	
	
	/**
	 * Sends a packet to the player that contains a Particle effect
	 * 
	 * @param effect to send
	 * @param loc to send to
	 * @param player to send to
	 */
	public void sendParticleEffectToPlayers(ParticleEffects effect, Location loc, Vector width, float data, int amount, Collection<Player> players){
		for(Player player : players){
			if(player.getLocation().getWorld() != loc.getWorld()
					|| player.getLocation().distanceSquared(loc) > maxRange) continue; 
			
			sendParticleEffect(effect, loc, width, data, amount, player);
		}
	}
	
	
	
	/**
	 * Sends the particle Effects to the Players for the Locations.
	 * 
	 * @param effect to send
	 * @param locations to use
	 * @param width to use
	 * @param data to use 
	 * @param amount to use
	 * @param players to send to
	 */
	public void sendParticleEffectsTo(ParticleEffects effect, Collection<Location> locations, Vector width, float data, int amount, Collection<Player> players){
		for(Location loc : locations){
			sendParticleEffectToPlayers(effect, loc, width, data, amount, players);
		}
	}
	
	/**
	 * Sends the particle Effects to the Players for the Locations.
	 * 
	 * @param effect to send
	 * @param locations to use
	 * @param width to use
	 * @param data to use 
	 * @param amount to use
	 * @param players to send to
	 */
	public void sendParticleEffectsTo(ParticleEffects effect, Collection<Location> locations, Vector width, float data, int amount, Player... players){
		sendParticleEffectsTo(effect, locations, width, data, amount, Arrays.asList(players));
	}
	
	
	/**
	 * Makes an arrow pickupable or not.
	 * 
	 * @param arrow to modify
	 * @param mayBePickedUp true if may be picked up. False if not.
	 */
	public abstract void makeArrowPickupable(Arrow arrow, boolean mayBePickedUp);
	
	
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
	

	/**
	 * Sets the Skin of a Player.
	 * 
	 * @param player to set
	 * @param skin to set
	 */
	public void setSkinOfPlayer(UtilsUsingPlugin plugin, Player player, String skin, String name){}
	
	
	/**
	 * Overrides the Entity AI to do nothing except for standing there and look.
	 * 
	 * @param entity to set.
	 */
	public abstract void overwriteAIToDoNothing(LivingEntity entity);
	
	
	/**
	 * Returns the Version Enum for this Vollotile Code.
	 * 
	 * @return the Version.
	 */
	public abstract MCVersion getVersion();
	
	
	/**
	 * Sets the Pages of a book to the TellRaw.
	 * 
	 * @param book to set
	 * @param pages to set.
	 */
	public void editBookToPages(ItemStack book, List<TellRawChatMessage> pages){
		//Not possible.
	}
	
	
	/**
	 * Sends a Message to the Action bar.
	 * 
	 * @param player to send to.
	 * @param message to send.
	 */
	public void sendActionBarMessage(Player player, String message){
		//To be overriden.
	}
	
	
	/**
	 * Lets an entity walk to an location.
	 * 
	 * @param entity to use
	 * @param location to use.
	 * @param speed to use.
	 * 
	 * @return true if worked. False if not.
	 */
	public boolean entityWalkToLocation(LivingEntity entity, Location location, double speed){
		//Dummy implementation.
		return false;
	}
	
	
	
	/**
	 * Removes the AttackDamage-Tag.
	 * 
	 * @param item to remove
	 * @return the Item with the removed tag.
	 */
	public ItemStack removeAttackDamageTag(ItemStack item){
		return item;
	}
	
	
	/**
	 * Converts to a Tellraw Message.
	 * 
	 * @param unconverted to use
	 * 
	 * @return the converted Message.
	 */
	public String convertToTellraw(String unconverted){
		return TellRawChatMessage.parse(unconverted).toString();
	}
	
	
	/**
	 * Sends the Title to the Player.
	 * 
	 * @param player to send
	 * @param title to send
	 * @param subtitle to send
	 */
	public void sendTitle(Player player, String title, String subtitle){}
	
	
	/**
	 * Resets the Title of a Player.
	 * 
	 * @param player
	 */
	public void resetTitle(Player player){}
	
	
	
	@Override
	public String toString(){
		return "Vollotile: " + CB_RELOCATION;
	}
	

	/**
	 * Generates a String for Json-ItemStack.
	 * @param item to parse
	 * @return the String to use.
	 */
	@SuppressWarnings("deprecation")
	public String generateItemIDString(ItemStack item){
		if(item == null) return "";
		return String.valueOf(item.getType().getId());
	}

	
	/**
	 * Returns the Language of the Player.
	 * <br>This is the language selected in the Settings.
	 * @return the language.
	 */
	public String getPlayerLanguage(Player player){
		return "en_US";
	}
	
	
	
	public static enum MCVersion{
		unknown,
		
		v1_6_R1,
		v1_6_R2,
		v1_6_R3,
		
		v1_7_R1,
		v1_7_R2,
		v1_7_R3,
		v1_7_R4,
		
		v1_8_R1,
		v1_8_R2,
		v1_8_R3,
		v1_9_R1,
		v1_9_R2,
		v1_10_R1,
		v1_11_R1;
		
		
		
		/**
		 * If the Version CALLED on is GREATER or EQUAL
		 * to the Version passed.
		 * <br>
		 * <br>Examples:
		 * <br>v1_8_R1.isVersionGreaterOrEqual(v1_7_R4) == true.
		 * <br>v1_8_R1.isVersionGreaterOrEqual(v1_8_R1) == true.
		 * <br>v1_7_R4.isVersionGreaterOrEqual(v1_8_R1) == false.
		 * 
		 * @param version to check.
		 * 
		 * @return
		 */
		public boolean isVersionGreaterOrEqual(MCVersion version){
			return ordinal() >= version.ordinal();
		}
		
		
		/**
		 * If the Version has Armorstands.
		 * 
		 * @return true if has armorstands.
		 */
		public boolean hasArmorstands(){
			return isVersionGreaterOrEqual(v1_8_R1);
		}
		
		/**
		 * If the Version has the Action bar.
		 * 
		 * @return true if present.
		 */
		public boolean hasActionBar(){
			return isVersionGreaterOrEqual(v1_8_R1);
		}
		
		/**
		 * If the Version has Title + Subtitle.
		 * 
		 * @return true if present.
		 */
		public boolean hasTitle(){
			return isVersionGreaterOrEqual(v1_8_R1);
		}

		
		/**
		 * If the Version has tell-Raw support.
		 * 
		 * @return true if present.
		 */
		public boolean hasTellRawSupport() {
			return isVersionGreaterOrEqual(v1_7_R3);
		}
		
	}
	
}
