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
package de.tobiyas.util.date;

import java.util.Calendar;
import java.util.Date;

public class DateComparisor {

	
	/**
	 * Checks if the Date passed is today.
	 * 
	 * @param date to check
	 * 
	 * @return true if the date passed is today
	 */
	public static boolean isToday(Date date){
		Calendar today = Calendar.getInstance();
		Calendar otherDate = Calendar.getInstance();
				otherDate.setTime(date);
				
		return today.get(Calendar.YEAR) == otherDate.get(Calendar.YEAR)
				  && today.get(Calendar.DAY_OF_YEAR) == otherDate.get(Calendar.DAY_OF_YEAR);
	}
}
