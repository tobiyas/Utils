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

import java.lang.reflect.Method;
import java.nio.charset.Charset;

import net.minecraft.server.v1_6_R3.Packet250CustomPayload;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_6_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import de.tobiyas.util.vollotile.ParticleEffects;
import de.tobiyas.util.vollotile.ReflectionsHelper;
import de.tobiyas.util.vollotile.VollotileCode;

public class MC_1_6_R3_VollotileCode extends VollotileCode {

	public MC_1_6_R3_VollotileCode() {
		super("v1_6_R3");
	}

	
	@Override
	public void playCriticalHitEffect(Player player, Entity toPlayEffect) {
		try{
			Object mcEntity = ReflectionsHelper.getMCEntityFromBukkitEntity(toPlayEffect);
			Object mcPlayer = ReflectionsHelper.getMCEntityFromBukkitEntity(player);
			
			Method playOutAnnimation= mcPlayer.getClass().getDeclaredMethod("b", Class.forName("net.minecraft.server." + CB_RELOCATION + ".Entity"));
			playOutAnnimation.invoke(mcPlayer, mcEntity);
		}catch(Exception exp){
		}
	}
	
	@Override
	public void sendParticleEffect(ParticleEffects effect, Location loc, Vector width, float speed, int amount, Player player) {
	}
	
	@Override
	public void sendCustomPayload(Player player, String channel, String message) {
		Packet250CustomPayload packet = new Packet250CustomPayload(channel, message.getBytes(Charset.forName("UTF-8")));
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}
}
