package de.tobiyas.util.command.params;

import java.lang.reflect.Parameter;

import org.bukkit.entity.Player;

import de.tobiyas.util.command.OptionalCommandParam;
import net.minecraft.server.v1_10_R1.Material;

public class CommandParamFactory {
	
	
	/**
	 * Generates the Param by the passed Parameter.
	 * @param param to generate from.
	 * 
	 * @return the Param class.
	 * 
	 * @throws ParameterNotSupportedException if not supported.
	 */
	public static AbstractCommandParam generateByArg(Parameter param) throws ParameterNotSupportedException {
		Class<?> type = param.getType();
		boolean optional = param.getAnnotationsByType(OptionalCommandParam.class) != null;
		String name = param.getName();
		
		if(type == Player.class) return new PlayerCommandParam(name, optional);
		if(type == Material.class) return new MaterialCommandParam(name, optional);
		
		if(type == String.class) return new StringCommandParam(name, optional);
		
		if(type == Integer.class || type == int.class) return new IntegerCommandParam(name, optional);
		if(type == Double.class || type == double.class) return new DoubleCommandParam(name, optional);
		if(type == Float.class || type == float.class) return new FloatCommandParam(name, optional);
		if(type == Boolean.class || type == boolean.class) return new BooleanCommandParam(name, optional);
		
		throw new ParameterNotSupportedException(param.getType());
	}

}
