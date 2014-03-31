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
package de.tobiyas.utils.tests.generate.monster;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.mockito.Mockito;

public class GenerateMonster {
	
	public static LivingEntity generateMonster(EntityType type){
		switch(type){
			case SKELETON: return generateSkeleton();
			default: System.out.println("Monster: " + type.getName() + " not implemented.");
		}
		
		return null;
	}

	public static Skeleton generateSkeleton(){
		Skeleton monster = Mockito.mock(Skeleton.class);
		Mockito.when(monster.getHealth()).thenReturn(10);
		Mockito.when(monster.getType()).thenReturn(EntityType.SKELETON);
		
		return monster;
	}
}
