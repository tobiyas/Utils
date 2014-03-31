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
package de.tobiyas.util.inventorymenu.stats;

public enum Operators {

	PLUS('+', 13),
	MINUS('-', 14),
	MULT('*', 1);
	
	private final char charValue;
	private final short woolColor;
	
	private Operators(char charValue, int woolColor) {
		this.charValue = charValue;
		this.woolColor = (short) woolColor;
	}
	
	public short getWoolColor(){
		return woolColor;
	}
	
	public char getCharValue(){
		return charValue;
	}
	
	
	/**
	 * Returns an Operator for a Char value
	 * 
	 * @param charValue
	 */
	public static Operators resolve(char charValue){
		for(Operators operator : values()){
			if(charValue == operator.charValue){
				return operator;
			}
		}
		
		return MULT;
	}
	
	/**
	 * Returns an Operator for a String value.
	 * It takes the first char.
	 * 
	 * null / empty Strings return {@link #MULT}
	 * 
	 * @param stringValue to search
	 */
	public static Operators resolve(String stringValue){
		if(stringValue == null || stringValue.length() == 0) return MULT;
		
		return resolve(stringValue.charAt(0));
	}
	
}
