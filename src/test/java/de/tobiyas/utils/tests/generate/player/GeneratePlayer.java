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
package de.tobiyas.utils.tests.generate.player;

import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Player.class)
public class GeneratePlayer {

	
	public static Player generatePlayer(String playerName){
		Player mockPlayer = Mockito.mock(Player.class);
		Mockito.when(mockPlayer.getName()).thenReturn(playerName);
		Mockito.when(mockPlayer.getDisplayName()).thenReturn(playerName);
		
		PlayerInventory mockInventory = Mockito.mock(PlayerInventory.class);
		Mockito.when(mockPlayer.getInventory()).thenReturn(mockInventory);
		
		return mockPlayer;
	}
}
