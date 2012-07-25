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
