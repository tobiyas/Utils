package de.tobiyas.util.vollotile.specific;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftArrow;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.util.Vector;

import de.tobiyas.util.chat.components.TellRawChatMessage;
import de.tobiyas.util.vollotile.ParticleEffects;
import de.tobiyas.util.vollotile.ReflectionsHelper;
import de.tobiyas.util.vollotile.VollotileCode;
import io.netty.buffer.ByteBuf;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagInt;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutCustomPayload;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_8_R3.PathEntity;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.EntityPlayer;

public class MC_1_8_R3_VollotileCode extends VollotileCode {

	public MC_1_8_R3_VollotileCode() {
		super("v1_8_R3");
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
		
		try{
			PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
					castParticle(effect), 
					true, 
					
					(float)loc.getX(), 
					(float)loc.getY(), 
					(float)loc.getZ(),
					
					(float)width.getX(),
					(float)width.getY(),
					(float)width.getZ(),
					
					speed,
					amount
					);

			((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
		}catch(Throwable exp){
			exp.printStackTrace();
		}
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
		craftArrow.getHandle().fromPlayer =  mayBePickedUp ? 1 : 0;
	}
	
	
	@Override
	public void setTabHeaderFooter(Player player, String header, String footer) {
		if(player == null) return;
		if(header == null) header = "";
		if(footer == null) footer = "";
		
		initHeaderFooterField();
		PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
		try{
			headerPacketField.set(packet, textToChatBase(header));
			footerPacketField.set(packet, textToChatBase(footer));
		}catch(Throwable exp){ exp.printStackTrace(); }
		
		
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutPlayerListHeaderFooter());
	}
	
	
	/**
	 * Converts a Text to an ChatBase.
	 * @param text to compile.
	 * @return the compiled text.
	 */
	private static IChatBaseComponent textToChatBase(String text){
		String compiledText = text.isEmpty() ? "{\"translate\":\"\"}" : "{\"text\":\""+text+"\"}";
		return IChatBaseComponent.ChatSerializer.a(compiledText);
	}
	
	
	private static Field headerPacketField;
	private static Field footerPacketField;
	
	
	private static void initHeaderFooterField(){
		if(footerPacketField != null) return;
		
		try{
			headerPacketField = PacketPlayOutPlayerListHeaderFooter.class.getDeclaredField("a");
			headerPacketField.setAccessible(true);
			
			footerPacketField = PacketPlayOutPlayerListHeaderFooter.class.getDeclaredField("b");
			footerPacketField.setAccessible(true);
		}catch(Throwable exp){ exp.printStackTrace(); }
	}

	
	
	private static EnumParticle castParticle(ParticleEffects effect){
		switch(effect){
		case ANGRY_VILLAGER:
			return EnumParticle.VILLAGER_ANGRY;
		case BUBBLE:
			return EnumParticle.WATER_BUBBLE;
		case CLOUD:
			return EnumParticle.CLOUD;
		case CRIT:
			return EnumParticle.CRIT;
		case DEPTH_SUSPEND:
			return EnumParticle.SUSPENDED_DEPTH;
		case DRIP_LAVA:
			return EnumParticle.DRIP_LAVA;
		case DRIP_WATER:
			return EnumParticle.DRIP_WATER;
		case ENCHANTMENT_TABLE:
			return EnumParticle.ENCHANTMENT_TABLE;
		case EXPLODE:
			return EnumParticle.EXPLOSION_NORMAL;
		case FIREWORKS_SPARK:
			return EnumParticle.FIREWORKS_SPARK;
		case FLAME:
			return EnumParticle.FLAME;
		case FOOTSTEP:
			return EnumParticle.FOOTSTEP;
		case HAPPY_VILLAGER:
			return EnumParticle.VILLAGER_HAPPY;
		case HEART:
			return EnumParticle.HEART;
		case HUGE_EXPLOSION:
			return EnumParticle.EXPLOSION_HUGE;
		case INSTANT_SPELL:
			return EnumParticle.SPELL_INSTANT;
		case LARGE_EXPLODE:
			return EnumParticle.EXPLOSION_LARGE;
		case LARGE_SMOKE:
			return EnumParticle.SMOKE_LARGE;
		case LAVA:
			return EnumParticle.LAVA;
		case MAGIC_CRIT:
			return EnumParticle.CRIT_MAGIC;
		case MOB_SPELL:
			return EnumParticle.SPELL_MOB;
		case MOB_SPELL_AMBIENT:
			return EnumParticle.SPELL_MOB_AMBIENT;
		case NOTE:
			return EnumParticle.NOTE;
		case PORTAL:
			return EnumParticle.PORTAL;
		case RED_DUST:
			return EnumParticle.REDSTONE;
		case SLIME:
			return EnumParticle.SLIME;
		case SNOWBALL_POOF:
			return EnumParticle.SNOWBALL;
		case SNOW_SHOVEL:
			return EnumParticle.SNOW_SHOVEL;
		case SPELL:
			return EnumParticle.SPELL;
		case SPLASH:
			return EnumParticle.WATER_SPLASH;
		case SUSPEND:
			return EnumParticle.SUSPENDED;
		case TOWN_AURA:
			return EnumParticle.TOWN_AURA;
		case WITCH_MAGIC:
			return EnumParticle.SPELL_WITCH;
		default:
			return EnumParticle.CRIT;
		}
		
	}



	@Override
	public void overwriteAIToDoNothing(LivingEntity entity) {
		CraftEntity lEntity = (CraftEntity) entity;
		net.minecraft.server.v1_8_R3.Entity eLiving = lEntity.getHandle();
		
		//set NO-AI flag.
		eLiving.getDataWatcher().watch(15, Byte.valueOf( (byte) 1 ));
	}
	
	
	@Override
	public void sendRawMessage(Player player, String rawMessage) {
		((CraftPlayer) player).getHandle().sendMessage(IChatBaseComponent.ChatSerializer.a(rawMessage));
	}
	
	@Override
	public MCVersion getVersion() {
		return MCVersion.v1_8_R3;
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
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag;
        if (!nmsStack.hasTag()) {
            tag = new NBTTagCompound();
            nmsStack.setTag(tag);
        } else {
            tag = nmsStack.getTag();
        }
        
        tag.set("HideFlags", new NBTTagInt(Integer.MAX_VALUE));
        nmsStack.setTag(tag);
        return CraftItemStack.asCraftMirror(nmsStack);
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
			PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a("\"" + title + "\""));
			pc.sendPacket(titlePacket);
		}
		
		if(subtitle != null && !subtitle.isEmpty()){
			PacketPlayOutTitle titlePacket2 = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, ChatSerializer.a("\"" + subtitle + "\""));
			pc.sendPacket(titlePacket2);
		}
	}
	
}
