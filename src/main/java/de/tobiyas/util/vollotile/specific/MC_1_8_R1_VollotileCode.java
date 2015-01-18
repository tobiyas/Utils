package de.tobiyas.util.vollotile.specific;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.lang.reflect.Method;
import java.nio.charset.Charset;

import net.minecraft.server.v1_8_R1.EnumParticle;
import net.minecraft.server.v1_8_R1.PacketDataSerializer;
import net.minecraft.server.v1_8_R1.PacketPlayOutCustomPayload;
import net.minecraft.server.v1_8_R1.PacketPlayOutWorldParticles;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftArrow;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import de.tobiyas.util.vollotile.ParticleEffects;
import de.tobiyas.util.vollotile.ReflectionsHelper;
import de.tobiyas.util.vollotile.VollotileCode;

public class MC_1_8_R1_VollotileCode extends VollotileCode {

	public MC_1_8_R1_VollotileCode() {
		super("v1_8_R1");
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
	public void sendCustomPayload(Player player, String channel, String message) {
		ByteBuf buffer = Unpooled.buffer();
		buffer.writeBytes(message.getBytes(Charset.forName("UTF-8")));
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
	
}
