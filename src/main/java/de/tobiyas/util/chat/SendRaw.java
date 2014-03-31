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
package de.tobiyas.util.chat;

import org.bukkit.entity.Player;

import de.tobiyas.util.vollotile.VollotileCodeManager;

public class SendRaw {

	/**
	 * Sends a raw message to the player passed.
	 * 
	 * @param player to send to
	 * @param message to send
	 */
	public static void sendPlayerRawMessage(Player player, String message){
		try{
			VollotileCodeManager.getVollotileCode().sendRawMessage(player, message);
			/*IChatBaseComponent component = ChatSerializer.a(message);
			((CraftPlayer) player) .getHandle().sendMessage(component);*/
		}catch(Throwable exp){
			player.sendMessage(message);
		}
	}

}
