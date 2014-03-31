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
package de.tobiyas.util.debug.erroruploader;

public enum ErrorUploaderResponceCode {

	error1(1, "No 'bukkit build' was submitted."),
	error2(2, "No 'StackTrace' was submitted."),
	error3(3, "No 'message' was submitted."),
	error4(4, "No 'class' was submitted."),
	error5(5, "No 'utils-Version' was submitted."),
	error6(6, "No 'plugin-Name' was submitted."),
	error7(7, "No 'plugin-Version' was submitted."),
	
	error20(20, "No connection to database possible."),
	error21(21, "No selected database not found or dead."),
	error22(22, "New Table for plugin could not be created."),
	error23(23, "Error message could not be stored."),
	
	error1337(1337, "unknown Error."),
	
	error0(0, "Success.");

	int errorNumber;
	String errorMessage;
	private ErrorUploaderResponceCode(int number, String message) {
		this.errorNumber = number;
		this.errorMessage = message;
	}
	
	private int getNumber(){
		return errorNumber;
	}
	
	private String getMessage(){
		return errorMessage;
	}
	
	
	//------------------------------//
	//static access
	
	public static String getMessageToError(String numberString){
		int number = 1337;
		try{
			number = Integer.parseInt(numberString);
		}catch(NumberFormatException e){}
		
		return getMessageToError(number);
	}
	
	public static String getMessageToError(int number){
		for(ErrorUploaderResponceCode code : values()){
			if(code.getNumber() == number)
				return code.errorMessage;
		}
		
		return error1337.getMessage();
	}
}
