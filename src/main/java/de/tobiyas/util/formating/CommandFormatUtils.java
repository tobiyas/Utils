package de.tobiyas.util.formating;

import java.util.List;

import de.tobiyas.util.math.Math2;

public class CommandFormatUtils {

	
	
	/**
	 * Gets the Page from the Data passed.
	 * <br>Assuming the Data is already sorted!
	 * 
	 * @param data to use
	 * @param page to use.
	 * @param pageSize to use
	 * @return the elements on that page.
	 */
	public static <T> List<T> getElements(List<T> data, int page, int pageSize){
		int maxPage = (int)Math.ceil((double)data.size() / (double)pageSize);
		page = Math2.clamp(1, page, maxPage);
		
		int maxIndex = data.size();
		int start = (page-1) * pageSize;
		int end = Math.min((page * pageSize) - 1, maxIndex);
		
		return data.subList(start, end);
	}
	
}
