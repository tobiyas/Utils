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
package de.tobiyas.util.vollotile.specific;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.List;

import net.minecraft.server.v1_7_R2.EntityHuman;
import net.minecraft.server.v1_7_R2.EntityInsentient;
import net.minecraft.server.v1_7_R2.EntityLiving;
import net.minecraft.server.v1_7_R2.PacketPlayOutCustomPayload;
import net.minecraft.server.v1_7_R2.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_7_R2.PathfinderGoalFloat;
import net.minecraft.server.v1_7_R2.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_7_R2.PathfinderGoalSelector;
import net.minecraft.server.v1_7_R2.PathEntity;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R2.entity.CraftArrow;
import org.bukkit.craftbukkit.v1_7_R2.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_7_R2.entity.CraftPlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import de.tobiyas.util.vollotile.ParticleEffects;
import de.tobiyas.util.vollotile.ReflectionsHelper;
import de.tobiyas.util.vollotile.VollotileCode;

public class MC_1_7_R2_VollotileCode extends VollotileCode {

	
	
	public MC_1_7_R2_VollotileCode() {
		super("v1_7_R2");
	}
	

	@Override
	public void playCriticalHitEffect(Player toSendTo, Entity toPlayEffect) {
		try{
			Object mcEntity = ReflectionsHelper.getMCEntityFromBukkitEntity(toPlayEffect);
			Object mcPlayer = ReflectionsHelper.getMCEntityFromBukkitEntity(toSendTo);
			
			Method playOutAnnimation= mcPlayer.getClass().getDeclaredMethod("b", Class.forName("net.minecraft.server." + CB_RELOCATION + ".Entity"));
			playOutAnnimation.invoke(mcPlayer, mcEntity);
		}catch(Exception exp){
		}
	}
	
	@Override
	public void sendParticleEffect(ParticleEffects effect, Location loc, Vector width, float speed, int amount, Player player) {
		if(width == null) width = new Vector();
		
		try{
			PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
					effect.getPacketArg(), (float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), 
					width.getBlockX(), width.getBlockY(), width.getBlockZ(), speed, amount);
			
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
		}catch(Throwable exp){
			exp.printStackTrace();
		}
	}
	
	@Override
	public void sendCustomPayload(Player player, String channel, String message) {
		PacketPlayOutCustomPayload packet = new PacketPlayOutCustomPayload(channel, message.getBytes(Charset.forName("UTF-8")));
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}
	
	@Override
	public void makeArrowPickupable(Arrow arrow, boolean mayBePickedUp) {
		if(arrow == null) return;
		
		CraftArrow craftArrow = (CraftArrow) arrow;
		craftArrow.getHandle().fromPlayer =  mayBePickedUp ? 1 : 0;
	}
	
	@Override
	public void overwriteAIToDoNothing(LivingEntity entity) {
		try {

			EntityLiving ev = ((CraftLivingEntity) entity).getHandle();

			Field goalsField = EntityInsentient.class.getDeclaredField("goalSelector");
			goalsField.setAccessible(true);
			PathfinderGoalSelector goals = (PathfinderGoalSelector) goalsField.get(ev);

			Field listField = PathfinderGoalSelector.class.getDeclaredField("b");
			
			listField.setAccessible(true);
			List list = (List) listField.get(goals);
			
			list.clear();
			listField = PathfinderGoalSelector.class.getDeclaredField("c");
			listField.setAccessible(true);
			list = (List) listField.get(goals);
			list.clear();

			goals.a(0, new PathfinderGoalFloat((EntityInsentient) ev));
			goals.a(1, new PathfinderGoalLookAtPlayer((EntityInsentient) ev, EntityHuman.class, 12.0F, 1.0F));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public MCVersion getVersion() {
		return MCVersion.v1_7_R2;
	}
	
	@Override
	public boolean entityWalkToLocation(LivingEntity entity, Location location, double speed){
		try{
			EntityInsentient nmsEntity = (EntityInsentient) ((CraftLivingEntity) entity).getHandle();
			PathEntity path = nmsEntity.getNavigation().a(location.getX(), location.getY(), location.getZ());
			nmsEntity.getNavigation().a(path, speed);
			return true;
		}catch(Throwable exp){
			return false;
		}
	}
}
