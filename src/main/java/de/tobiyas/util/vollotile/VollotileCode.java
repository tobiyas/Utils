package de.tobiyas.util.vollotile;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

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
	 * Sends a Raw Message to the player passed.
	 * 
	 * @param player to send
	 */
	public void sendRawMessage(Player player, String rawMessage){
		try{
			String seperator = ".";

			Object mcPlayer = getMCEntityFromBukkitEntity(player);
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
		Object mcPlayer = getMCEntityFromBukkitEntity(entity);
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

	
	////////////////////////
	//Helper Methods Below//
	////////////////////////
	
	
	/**
	 * Gets the MC entity from the Bukkit entity.
	 * 
	 * @param entity to get from
	 * 
	 * @return the MC entity.
	 */
	protected Object getMCEntityFromBukkitEntity(Entity entity){
		try{
			Method getHandle = entity.getClass().getDeclaredMethod("getHandle");
			Object mcEntity = getHandle.invoke(entity);
			
			return mcEntity;
		}catch(Exception exp){
			exp.printStackTrace();
			return null;
		}
	}
}
