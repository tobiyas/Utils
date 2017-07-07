package de.tobiyas.util.formating;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.tobiyas.util.chat.components.TellRawChatMessage;
import de.tobiyas.util.math.Math2;

public class CommandFormatUtils {

	
	
	/**
	 * Gets the Page from the Data passed.
	 * <br>Assuming the Data is already sorted!
	 * <br>Pages start at 1.
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
	
	
	
	public static class PagePackage<T> {
		
		/**
		 * The set of sorted stuff.
		 */
		private final List<T> sorted;
		
		/**
		 * The size of the Page
		 */
		private final int pageSize;
		
		/**
		 * The last page of the Package.
		 */
		private final int lastPage;
		
		
		public PagePackage(Collection<T> collection, Comparator<T> sorter, int pageSize){
			this.sorted = new ArrayList<T>(collection);
			Collections.sort(sorted, sorter);
			
			this.pageSize = pageSize;
			
			double size = sorted.size();
			double pSize = pageSize;
			this.lastPage = (int) Math.ceil(size / pSize);
		}
		
		
		/**
		 * gets the Page for the Data.
		 * @param page to get.
		 * @return the data for the page.
		 */
		public List<T> getPage(int page){
			page = Math2.clamp(1, page, lastPage);
			
			int maxIndex = sorted.size();
			int start = (page-1) * pageSize;
			int end = Math.min((page * pageSize), maxIndex);
			
			return sorted.subList(start, end);
		}
		
		
		/**
		 * Gets the last page of the Package.
		 * @return the last page.
		 */
		public int getLastPage() {
			return lastPage;
		}
		
		/**
		 * Gets the amount of items in the page.
		 * @return the amount of items on a page.
		 */
		public int getPageSize() {
			return pageSize;
		}
		
		
		/**
		 * Generates a Bottom row / Line with Tell-Raw.
		 * @param page to set.
		 * @param command to  use. This will be formated!!!! use %s for the page number!!
		 * @return the generated Chat Message to send.
		 */
		public TellRawChatMessage generateBottomLine(int page, String command){
			TellRawChatMessage message = new TellRawChatMessage().addSimpleText("        ");
			
			if(page > 1) message = message.addSimpleCommand("<<", "Page " + (page - 1), String.format(command, page - 1));
			else message = message.addSimpleText("    ");
			
			message = message.addSimpleText(" | ");
			
			if(page < getLastPage()) message = message.addSimpleCommand(">>", "Page " + (page + 1), String.format(command, page + 1));
			
			return message;
		}
		
		
	}
	
}