package de.tobiyas.util.vollotile.specific;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftArrow;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import com.google.gson.JsonObject;

import de.tobiyas.util.chat.components.TellRawChatMessage;
import de.tobiyas.util.vollotile.ParticleEffects;
import de.tobiyas.util.vollotile.ReflectionsHelper;
import de.tobiyas.util.vollotile.VollotileCode;
import io.netty.buffer.ByteBuf;
import net.minecraft.server.v1_9_R1.BlockPosition;
import net.minecraft.server.v1_9_R1.EntityArrow.PickupStatus;
import net.minecraft.server.v1_9_R1.EntityInsentient;
import net.minecraft.server.v1_9_R1.EntityPlayer;
import net.minecraft.server.v1_9_R1.IChatBaseComponent;
import net.minecraft.server.v1_9_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_9_R1.Item;
import net.minecraft.server.v1_9_R1.MinecraftKey;
import net.minecraft.server.v1_9_R1.PacketDataSerializer;
import net.minecraft.server.v1_9_R1.PacketPlayOutChat;
import net.minecraft.server.v1_9_R1.PacketPlayOutCustomPayload;
import net.minecraft.server.v1_9_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_9_R1.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_9_R1.PathEntity;
import net.minecraft.server.v1_9_R1.PlayerConnection;

public class MC_1_9_R1_VollotileCode extends VollotileCode {

	public MC_1_9_R1_VollotileCode() {
		super("v1_9_R1");
	}



	@Override
	public void playCriticalHitEffect(Player toSendTo, Entity toPlayEffect) {
		try{
			Object mcEntity = ReflectionsHelper.getMCEntityFromBukkitEntity(toPlayEffect);
			Object mcPlayer = ReflectionsHelper.getMCEntityFromBukkitEntity(toSendTo);
			
			Method playOutAnnimation= mcPlayer.getClass().getDeclaredMethod("b", Class.forName("net.minecraft.server." + CB_RELOCATION + ".Entity"));
			playOutAnnimation.invoke(mcPlayer, mcEntity);
		}catch(Exception exp){}
	}
	
	
	@Override
	public void sendParticleEffect(ParticleEffects effect, Location loc, Vector width, float speed, int amount, Player player) {
		if(width == null) width = new Vector();
		
		player.spawnParticle(
				castParticle(effect), 
				loc.getX(), 
				loc.getY(), 
				loc.getZ(), 
				
				amount, 
				width.getX(), 
				width.getY(), 
				width.getZ(), 
				
				speed);
	}
	
	@Override
	public void sendCustomPayload(Player player, String channel, ByteBuf buffer) {
		PacketDataSerializer serializer = new PacketDataSerializer(buffer);
		PacketPlayOutCustomPayload packet = new PacketPlayOutCustomPayload(channel, serializer);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}


	@Override
	public void makeArrowPickupable(Arrow arrow, boolean mayBePickedUp) {
		if(arrow == null) return;
		
		CraftArrow craftArrow = (CraftArrow) arrow;
		craftArrow.getHandle().fromPlayer =  mayBePickedUp ? PickupStatus.ALLOWED : PickupStatus.DISALLOWED;
	}

	
	
	private static Particle castParticle(ParticleEffects effect){
		switch(effect){
		case ANGRY_VILLAGER:
			return Particle.VILLAGER_ANGRY;
		case BUBBLE:
			return Particle.WATER_BUBBLE;
		case CLOUD:
			return Particle.CLOUD;
		case CRIT:
			return Particle.CRIT;
		case DEPTH_SUSPEND:
			return Particle.SUSPENDED_DEPTH;
		case DRIP_LAVA:
			return Particle.DRIP_LAVA;
		case DRIP_WATER:
			return Particle.DRIP_WATER;
		case ENCHANTMENT_TABLE:
			return Particle.ENCHANTMENT_TABLE;
		case EXPLODE:
			return Particle.EXPLOSION_NORMAL;
		case FIREWORKS_SPARK:
			return Particle.FIREWORKS_SPARK;
		case FLAME:
			return Particle.FLAME;
		case FOOTSTEP:
			return Particle.FOOTSTEP;
		case HAPPY_VILLAGER:
			return Particle.VILLAGER_HAPPY;
		case HEART:
			return Particle.HEART;
		case HUGE_EXPLOSION:
			return Particle.EXPLOSION_HUGE;
		case INSTANT_SPELL:
			return Particle.SPELL_INSTANT;
		case LARGE_EXPLODE:
			return Particle.EXPLOSION_LARGE;
		case LARGE_SMOKE:
			return Particle.SMOKE_LARGE;
		case LAVA:
			return Particle.LAVA;
		case MAGIC_CRIT:
			return Particle.CRIT_MAGIC;
		case MOB_SPELL:
			return Particle.SPELL_MOB;
		case MOB_SPELL_AMBIENT:
			return Particle.SPELL_MOB_AMBIENT;
		case NOTE:
			return Particle.NOTE;
		case PORTAL:
			return Particle.PORTAL;
		case RED_DUST:
			return Particle.REDSTONE;
		case SLIME:
			return Particle.SLIME;
		case SNOWBALL_POOF:
			return Particle.SNOWBALL;
		case SNOW_SHOVEL:
			return Particle.SNOW_SHOVEL;
		case SPELL:
			return Particle.SPELL;
		case SPLASH:
			return Particle.WATER_SPLASH;
		case SUSPEND:
			return Particle.SUSPENDED;
		case TOWN_AURA:
			return Particle.TOWN_AURA;
		case WITCH_MAGIC:
			return Particle.SPELL_WITCH;
		default:
			return Particle.CRIT;
		}
		
	}



	@Override
	public void overwriteAIToDoNothing(LivingEntity entity) {
		CraftEntity lEntity = (CraftEntity) entity;
		net.minecraft.server.v1_9_R1.Entity eLiving = lEntity.getHandle();
		if(eLiving instanceof EntityInsentient){
			EntityInsentient iEntity = (EntityInsentient) eLiving;
			iEntity.m(true);
		}
	}
	
	
	@Override
	public void sendRawMessage(Player player, String rawMessage) {
		((CraftPlayer) player).getHandle().sendMessage(IChatBaseComponent.ChatSerializer.a(rawMessage));
	}
	
	@Override
	public MCVersion getVersion() {
		return MCVersion.v1_9_R1;
	}
	
	
	@Override
	public void editBookToPages(ItemStack book, List<TellRawChatMessage> pages) {
		if(book == null || book.getType() != Material.WRITTEN_BOOK) return;
		
		try{
			BookMeta meta = (BookMeta) book.getItemMeta();
			Field field = null;
			
			Class<?> clazz = meta.getClass();
			while(clazz != null){
				for(Field declared : clazz.getDeclaredFields()){
					if(declared.getName().equals("pages")){
						field = declared;
						declared.setAccessible(true);
						break;
					}
				}
				
				clazz = clazz.getSuperclass();
				if(field != null) break;
			}
			
			if(field == null) return;
			@SuppressWarnings("unchecked")
			List<IChatBaseComponent> metaPages = (List<IChatBaseComponent>) field.get(meta);
			
			for(TellRawChatMessage page : pages){
				metaPages.add(ChatSerializer.a(page.buildBook()));
			}
			
			book.setItemMeta(meta);
		}catch(Throwable exp){
			exp.printStackTrace();
		}
	}
	
	
	@Override
	public void sendActionBarMessage(Player player, String message) {
		if(!player.isOnline()) return;
		
		try{
			PacketPlayOutChat packet = new PacketPlayOutChat(ChatSerializer.a("{\"text\": \"" + message + "\"}"), (byte)2);
			((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
		}catch(Throwable exp){
			//Silently ignore error since it would spam a lot!
		}
	}
	
	
	@Override
	public boolean entityWalkToLocation(LivingEntity entity, Location location, double speed){
		try{
			EntityInsentient nmsEntity = (EntityInsentient) ((CraftLivingEntity) entity).getHandle();
			PathEntity path = nmsEntity.getNavigation().a(new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
			nmsEntity.getNavigation().a(path, speed);
			return true;
		}catch(Throwable exp){
			return false;
		}
	}
	
	
	@Override
	public ItemStack removeAttackDamageTag(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		item.setItemMeta(meta);
		return item;
	}
	
	
	
	@Override
	public void resetTitle(Player player){
		PlayerConnection pc = ((CraftPlayer)player).getHandle().playerConnection;
		PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(EnumTitleAction.RESET, null);
		pc.sendPacket(titlePacket);
	}
	
	
	@Override
	public void sendTitle(Player player, String title, String subtitle) {
		title = ChatColor.translateAlternateColorCodes('&', title);
		subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
		
		PlayerConnection pc = ((CraftPlayer)player).getHandle().playerConnection;
		if(title != null && !title.isEmpty()){
			JsonObject obj = new JsonObject();
			obj.addProperty("text", title);
			PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a(obj.toString()));
			pc.sendPacket(titlePacket);
		}
		
		if(subtitle != null && !subtitle.isEmpty()){
			JsonObject obj = new JsonObject();
			obj.addProperty("text", subtitle);
			PacketPlayOutTitle titlePacket2 = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, ChatSerializer.a(obj.toString()));
			pc.sendPacket(titlePacket2);
		}
	}
	
	
	/**
	 * The Field containing the Language.
	 */
	private Field localeField;
	
	
	@Override
	public String getPlayerLanguage(Player player) {
		if(localeField == null){
			try{
				Field field = EntityPlayer.class.getDeclaredField("locale");
				field.setAccessible(true);
				localeField = field;
			}catch(Throwable exp){
				return "en_US";
			}
		}
		
		EntityPlayer ePl = ((CraftPlayer) player).getHandle();
		try{
			return localeField.get(ePl).toString();
		}catch(Throwable exp) { return "en_US"; }
	}
	
	
	@SuppressWarnings("deprecation")
	@Override
	public String generateItemIDString(ItemStack item) {
		if(item == null) return "";
		
		Item nmsMat = Item.getById(item.getType().getId());
		MinecraftKey key = Item.REGISTRY.b(nmsMat);
		return "\\\""+key.toString()+"\\\"";
	}
	
}
