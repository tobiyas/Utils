package de.tobiyas.util.potion;

import org.bukkit.potion.PotionEffectType;

public class PotionUtils {

	/**
	 * Translates the Type to a german name.
	 * 
	 * @param potionType to convert.
	 * @return the name or empty String if not possible.
	 */
	@SuppressWarnings("deprecation")
	public static String getGermanName(PotionEffectType potionType){
		if( potionType ==  null) return "";
		
		if( potionType.getId() == PotionEffectType.ABSORPTION.getId() ) return "Absorbation";
		if( potionType.getId() == PotionEffectType.BLINDNESS.getId() ) return "Blindheit";
		if( potionType.getId() == PotionEffectType.CONFUSION.getId() ) return "Konfusion";
		if( potionType.getId() == PotionEffectType.DAMAGE_RESISTANCE.getId() ) return "Schadends Resistenz";
		if( potionType.getId() == PotionEffectType.FAST_DIGGING.getId() ) return "Schnelles Graben";
		if( potionType.getId() == PotionEffectType.FIRE_RESISTANCE.getId() ) return "Feuer Resistenz";
		if( potionType.getId() == PotionEffectType.HARM.getId() ) return "Schaden";
		if( potionType.getId() == PotionEffectType.HEAL.getId() ) return "Heilung";
		if( potionType.getId() == PotionEffectType.HEALTH_BOOST.getId() ) return "Leben";
		if( potionType.getId() == PotionEffectType.HUNGER.getId() ) return "Hunger";
		if( potionType.getId() == PotionEffectType.INCREASE_DAMAGE.getId() ) return "Erhöhter Schaden";
		if( potionType.getId() == PotionEffectType.INVISIBILITY.getId() ) return "Unsichtbarkeit";
		if( potionType.getId() == PotionEffectType.JUMP.getId() ) return "Sprungkraft";
		if( potionType.getId() == PotionEffectType.NIGHT_VISION.getId() ) return "Nachtsicht";
		if( potionType.getId() == PotionEffectType.POISON.getId() ) return "Gift";
		if( potionType.getId() == PotionEffectType.REGENERATION.getId() ) return "Regeneration";
		if( potionType.getId() == PotionEffectType.SATURATION.getId() ) return "Sättigung";
		if( potionType.getId() == PotionEffectType.SLOW.getId() ) return "Langsamkeit";
		if( potionType.getId() == PotionEffectType.SPEED.getId() ) return "Geschwindigkeit";
		if( potionType.getId() == PotionEffectType.WATER_BREATHING.getId() ) return "Wasseratmung";
		if( potionType.getId() == PotionEffectType.WEAKNESS.getId() ) return "Schwäche";
		if( potionType.getId() == PotionEffectType.WITHER.getId() ) return "Wither";
		
		return potionType.getName();
	}
	
}
