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

import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import de.tobiyas.util.vollotile.ParticleEffects;
import de.tobiyas.util.vollotile.VollotileCode;

/**
 * This is a dummy implementation for some dummy
 * 
 * @author tobiyas
 */
public class UNKNOWN_VollotileCode extends VollotileCode {

	public UNKNOWN_VollotileCode() {
		super("UNKNOWN"); //We have no clue.
	}
	
	@Override
	public void playCriticalHitEffect(Player toSendTo, Entity toPlayEffect) {
	}

	@Override
	public void removeParticleEffect(LivingEntity entity) {
	}
	
	
	@Override
	public void sendParticleEffect(ParticleEffects effect, Location loc, Vector width, float speed, int amount, Player player) {
	}
	
	@Override
	public void sendCustomPayload(Player player, String channel, String message) {
	}

	@Override
	public void makeArrowPickupable(Arrow arrow, boolean mayBePickedUp) {
	}
}
