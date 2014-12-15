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
package de.tobiyas.util.vollotile;

import de.tobiyas.util.vollotile.specific.MC_1_6_R1_VollotileCode;
import de.tobiyas.util.vollotile.specific.MC_1_6_R2_VollotileCode;
import de.tobiyas.util.vollotile.specific.MC_1_6_R3_VollotileCode;
import de.tobiyas.util.vollotile.specific.MC_1_7_R1_VollotileCode;
import de.tobiyas.util.vollotile.specific.MC_1_7_R2_VollotileCode;
import de.tobiyas.util.vollotile.specific.MC_1_7_R3_VollotileCode;
import de.tobiyas.util.vollotile.specific.MC_1_7_R4_VollotileCode;
import de.tobiyas.util.vollotile.specific.MC_1_8_R1_VollotileCode;
import de.tobiyas.util.vollotile.specific.UNKNOWN_VollotileCode;

public class VollotileCodeManager {

	/**
	 * The VollotileCode for the Plugin
	 */
	private static VollotileCode code;
	
	/**
	 * Returns the Vollotile code for the Current version.
	 * 
	 * @return
	 */
	public static VollotileCode getVollotileCode(){
		if(code == null){
			initCode();
			System.out.println("Loaded Vollotile Code for: " + code.CB_RELOCATION);
		}
		
		return code;
	}

	/**
	 * Inits the Vollotile Code entry.
	 */
	private static void initCode() {
		String ver = getRelocationAddition();
		
		if(ver == null) {
			code = new UNKNOWN_VollotileCode();
			System.out.println("Could not find a Vollotile for the Current MC Version. Using Fallback.");
		}
		
		
		if(ver.equalsIgnoreCase("v1_8_R1")){
			code = new MC_1_8_R1_VollotileCode();
			return;
		}
		
		if(ver.equalsIgnoreCase("v1_7_R4")){
			code = new MC_1_7_R4_VollotileCode();
			return;
		}
		
		if(ver.equalsIgnoreCase("v1_7_R3")){
			code = new MC_1_7_R3_VollotileCode();
			return;
		}
		
		if(ver.equalsIgnoreCase("v1_7_R2")){
			code = new MC_1_7_R2_VollotileCode();
			return;
		}
		
		if(ver.equalsIgnoreCase("v1_7_R1")){
			code = new MC_1_7_R1_VollotileCode();
			return;
		}
		
		if(ver.equalsIgnoreCase("v1_6_R3")){
			code = new MC_1_6_R3_VollotileCode();
			return;
		}
		
		if(ver.equalsIgnoreCase("v1_6_R2")){
			code = new MC_1_6_R2_VollotileCode();
			return;
		}
		
		if(ver.equalsIgnoreCase("v1_6_R1")){
			code = new MC_1_6_R1_VollotileCode();
			return;
		}
		
		if(code == null) {
			code = new UNKNOWN_VollotileCode();
			System.out.println("Could not find a Vollotile for the Current MC Version. Using Fallback.");
		}
	}
	
	/**
	 * The relocation addition for the classes.
	 */
	private static String relocationAddition;
	
	/**
	 * Gets the rev used.
	 * @return
	 */
	public static String getRelocationAddition(){
		if(relocationAddition != null) return relocationAddition;
		
		for(int main = 0; main < 10; main++){
			for(int sub = 0; sub < 20; sub++){
				for(int rev = 0; rev < 20; rev++){
					try{
						String addition = "v" + main + "_" + sub + "_R" + rev;
						Class.forName("net.minecraft.server." + addition +  ".Entity");
						relocationAddition = addition;
						return relocationAddition;
					}catch(Throwable exp){}
				}
			}
		}
		
		return null;
	}
	
	
}
