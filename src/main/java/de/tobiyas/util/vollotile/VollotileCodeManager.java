package de.tobiyas.util.vollotile;

import de.tobiyas.util.vollotile.specific.MC_1_6_R1_VollotileCode;
import de.tobiyas.util.vollotile.specific.MC_1_6_R2_VollotileCode;
import de.tobiyas.util.vollotile.specific.MC_1_6_R3_VollotileCode;
import de.tobiyas.util.vollotile.specific.MC_1_7_R1_VollotileCode;
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
		}
		
		return code;
	}

	/**
	 * Inits the Vollotile Code entry.
	 */
	private static void initCode() {
		VollotileCode toCheck = null;
		
		toCheck = new MC_1_7_R1_VollotileCode();
		if(toCheck.isCorrectVersion()){
			code = toCheck;
			return;
		}

		toCheck = new MC_1_6_R3_VollotileCode();
		if(toCheck.isCorrectVersion()){
			code = toCheck;
			return;
		}
		
		toCheck = new MC_1_6_R2_VollotileCode();
		if(toCheck.isCorrectVersion()){
			code = toCheck;
			return;
		}
		
		toCheck = new MC_1_6_R1_VollotileCode();
		if(toCheck.isCorrectVersion()){
			code = toCheck;
			return;
		}

		code = new UNKNOWN_VollotileCode();
	}
	
	
	
}
