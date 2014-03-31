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
package de.tobiyas.util.items;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;

public class MaterialParser {

	
	/**
	 * Parses the passed String list to a Set of Materials.
	 * Not recognized Materials are ignored.
	 * 
	 * @param arg to parse
	 * @return the parsed Set with materials.
	 */
	public static Set<Material> parseToMaterial(List<String> args){
		Set<Material> mats = new HashSet<Material>();
		
		for(String arg : args){
			Material mat = identifyMat(arg);
			if(mat != null && !mats.contains(mat)){
				mats.add(mat);
			}
		}
		
		return mats;
	}

	/**
	 * Parses a Material from a String.
	 * <br>This can either be a {@link Material} or an ID of an Material.
	 * <br>If none can be identified, <code>null</code> is returned.
	 * 
	 * @param arg to parse
	 * 
	 * @return the parsed Material.
	 */
	private static Material identifyMat(String arg) {
		//first try to check if it is an ID
		try{
			int id = Integer.parseInt(arg);
			Material mat = Material.getMaterial(id);
			return mat;
		}catch(NumberFormatException exp){}
		
		try{
			Material mat = Material.valueOf(arg.toUpperCase());
			return mat;
		}catch(IllegalArgumentException exp){}
		
		//none of the detections found a material.
		return null;
	}
}
