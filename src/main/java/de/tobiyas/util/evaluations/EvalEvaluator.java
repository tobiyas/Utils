package de.tobiyas.util.evaluations;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import de.tobiyas.util.evaluations.parts.Calculation;
import de.tobiyas.util.evaluations.parts.Operator;
import de.tobiyas.util.evaluations.parts.leafs.Number;
import de.tobiyas.util.evaluations.parts.leafs.Variable;
import de.tobiyas.util.evaluations.parts.operators.Add;
import de.tobiyas.util.evaluations.parts.operators.Divide;
import de.tobiyas.util.evaluations.parts.operators.Multiply;
import de.tobiyas.util.evaluations.parts.operators.Subtract;

public class EvalEvaluator {

	private static final Collection<Character> operators = Arrays.asList('+','-','*','/');
	private static final Collection<Character> bracket = Arrays.asList('(',')');
	
	/**
	 * Splits the String by number / eval expressions.
	 * @param toSplit the String to split.
	 * @return the split String.
	 */
	private static List<String> splitStuff(String toSplit){
		toSplit = toSplit.replace(" ", "");
		
		List<String> splittet = new ArrayList<String>();
		if(toSplit.isEmpty()) return splittet;
		
		int index = 0;
		String currentData = "";
		boolean isCurrentlyVariable = false;
		
		while(index < toSplit.length()){
			char currentChar = toSplit.charAt(index);
			index++;
			
			//Check for variable end:
			if((currentChar == '%' || currentChar == '}') && isCurrentlyVariable){
				currentData += currentChar;
				splittet.add(currentData);
				currentData = "";
				isCurrentlyVariable = false;
				continue;
			}
			
			//Check for Variable start:
			if((currentChar == '%' || currentChar == '{') && !isCurrentlyVariable){
				currentData += currentChar;
				isCurrentlyVariable = true;
				continue;
			}
			
			//Check for Operator:
			if(operators.contains(currentChar)){
				if(!currentData.isEmpty())splittet.add(currentData);
				currentData = "";
				splittet.add(String.valueOf(currentChar));
				continue;
			}
			
			//Check if is Bracket.
			if(bracket.contains(currentChar)){
				if(!currentData.isEmpty())splittet.add(currentData);
				currentData = "";
				splittet.add(String.valueOf(currentChar));
				continue;
			}
			
			//Well we have a number or something like that...
			currentData += currentChar;
		}
		
		return splittet;
	}
	

	/**
	 * Checks if all elements in the eval part are valid on it's own.
	 */
	private static List<String> getWrongElements(List<String> toCheck){
		List<String> wrongElements = new LinkedList<String>();
		for(String key : toCheck){
			//check for Operator or bracket:
			if(key.length() == 1){
				char singleChar = key.charAt(0);
				if(bracket.contains(singleChar)) continue;
				if(operators.contains(singleChar)) continue;
			}
			
			//check if is variable:
			if(key.length() > 2 && key.startsWith("%") && key.endsWith("%")) continue;
			if(key.length() > 2 && key.startsWith("{") && key.endsWith("}")) continue;
			
			//Last check if we have a number:
			try{ Double.parseDouble(key); continue; }catch(Throwable exp){} 
			
			//It's nothing that we know!
			wrongElements.add(key);
		}
		
		return wrongElements;
	}
	
	/**
	 * Checks if the bracket Syntax is somehow Correct.
	 */
	private static boolean hasCorrectBracketSynthax(List<String> toCheck){
		int currentOpenBraket = 0;
		
		int braketOpen = 0;
		int braketClose = 0;
		for(String key : toCheck){
			boolean isOpen = key.equals("(");
			boolean isClose = key.equals(")");
			
			if(isOpen){
				braketOpen++;
				currentOpenBraket++;
			}
			
			if(isClose){
				braketClose++;
				currentOpenBraket--;
				if(currentOpenBraket < 0) return false;
			}
		}
		
		return currentOpenBraket == 0 && braketOpen == braketClose;
	}
	
	
	/**
	 * Parses from the String passed.
	 * @param toParse the String to parse.
	 * @return the Calculation generated.
	 * @throws IllegalArgumentException thrown if not parseable.
	 */
	public static Calculation parse(String toParse) throws IllegalArgumentException {
		//put brackets around. To be sure!
		toParse = "("+toParse+")";
		
		List<String> parsed = splitStuff(toParse);
		return parse(parsed);
	}
	
	
	/**
	 * Parses from the String passed.
	 * @param toParse the String to parse.
	 * @return the Calculation generated.
	 * @throws IllegalArgumentException thrown if not parseable.
	 */
	private static Calculation parse(List<String> toParse) throws IllegalArgumentException {
		if(toParse == null || toParse.isEmpty()) throw new IllegalArgumentException("Calculation is empty!");
		if(!hasCorrectBracketSynthax(toParse)) throw new IllegalArgumentException("Brakets are not correct!");
		
		List<String> wrongElements = getWrongElements(toParse);
		if(!wrongElements.isEmpty()){
			throw new IllegalArgumentException("Some elements are not correct: " + StringUtils.join(wrongElements, " "));
		}
		
		List<Object> calculations = new ArrayList<Object>(toParse);
		replaceLeafes(calculations);
		replaceOperators(calculations);
		calculations = parseBrackets(calculations.iterator());
		
		return simplifyList(calculations);
	}
	
	
	/**
	 * Replaces all leaves with the responsive leaves.
	 * @param calculations to use.
	 */
	private static void replaceLeafes(List<Object> calculations) {
		for(int i = 0; i < calculations.size(); i++){
			Object toCheck = calculations.get(i);
			if(toCheck instanceof Calculation) continue;
			
			String toConvert = toCheck.toString();
			//First check for Number:
			try{
				double number = Double.parseDouble(toConvert);
				calculations.set(i, new Number(number));
				continue;
			}catch(Throwable exp){}
			
			//Second check for Variables:
			if(toConvert.length() > 2 && toConvert.startsWith("%") && toConvert.endsWith("%")) {
				calculations.set(i, new Variable(toConvert));
				continue;
			}
			
			//Second check for Variables:
			if(toConvert.length() > 2 && toConvert.startsWith("{") && toConvert.endsWith("}")) {
				calculations.set(i, new Variable(toConvert));
				continue;
			}
		}
	}

	
	/**
	 * Replaces all Multiplies with the responsive Multiply.
	 * @param calculations to use.
	 */
	private static void replaceOperators(List<Object> calculations) {
		for(int i = 0; i < calculations.size(); i++){
			Object toCheck = calculations.get(i);
			if(toCheck instanceof Calculation) continue;
			
			String toParse = toCheck.toString();
			if(toParse.equals("+")) calculations.set(i, new Add());
			if(toParse.equals("-")) calculations.set(i, new Subtract());
			if(toParse.equals("*")) calculations.set(i, new Multiply());
			if(toParse.equals("/")) calculations.set(i, new Divide());
		}
	}
	
	
	/**
	 * parses the List of Objects (everything expect Brackets) to List<List>... to lower-cast.
	 * @param it to parse
	 * @return the List containing Lists of List of ... Calculations or Calculations.
	 */
	private static List<Object> parseBrackets(Iterator<Object> it){
		List<Object> result = new LinkedList<Object>();
		while(it.hasNext()){
			Object obj = it.next();
			
			//If no String -> Not interesting!
			if(obj instanceof Calculation) {
				result.add(obj);
				continue;
			}
			
			String toCheck = (String) obj;
			if(toCheck.equals("(")){
				List<Object> inside = parseBrackets(it);
				result.add(inside);
				continue;
			}
			
			if(toCheck.equals(")")) return result;
		}
		
		return result;
	}
	
	
	/**
	 * Simplifies a list.
	 * @param list to simplify
	 * @return the calculation.
	 */
	@SuppressWarnings("unchecked")
	private static Calculation simplifyList(List<Object> list){
		//Only simplify the a list of PLAIN calculations!
		List<Calculation> calcs = new LinkedList<Calculation>();
		for(Object obj : list) {
			if(obj instanceof List) calcs.add(simplifyList((List<Object>) obj));
			else if(obj instanceof Calculation) calcs.add((Calculation) obj);
			else throw new RuntimeException("Was not a list or Calculation!");
		}
		
		//First right-bind Multiplications:
		boolean gotOne = true;
		while(gotOne){
			gotOne = false;
			for(int i = calcs.size()-2; i >= 0; i--){
				Calculation current = calcs.get(i);
				Calculation right = calcs.get(i+1);
				
				if(current instanceof Multiply || current instanceof Divide){
					Operator operator = (Operator) current;
					if(operator.getPart2() != null) continue;
					
					operator.setPart2(right);
					calcs.remove(i+1);
					gotOne = true;
					break;
				}

			}
		}
		
		//Second left-bind Multiplications:
		gotOne = true;
		while(gotOne){
			gotOne = false;
			for(int i = 1; i < calcs.size(); i++){
				Calculation current = calcs.get(i);
				Calculation left = calcs.get(i-1);
				
				if(current instanceof Multiply || current instanceof Divide){
					Operator operator = (Operator) current;
					if(operator.getPart1() != null) continue;
					
					operator.setPart1(left);
					calcs.remove(i-1);
					gotOne = true;
					break;
				}
				
			}
		}
		
		//Third right-bind Additions / Subtractions:
		gotOne = true;
		while(gotOne){
			gotOne = false;
			for(int i = calcs.size()-2; i >= 0; i--){
				Calculation current = calcs.get(i);
				Calculation right = calcs.get(i+1);
				
				if(current instanceof Add || current instanceof Subtract){
					Operator operator = (Operator) current;
					if(operator.getPart2() != null) continue;
					
					operator.setPart2(right);
					calcs.remove(i+1);
					gotOne = true;
					break;
				}
				
			}
		}
		
		//Last left-bind Additions / Subtractions:
		gotOne = true;
		while(gotOne){
			gotOne = false;
			for(int i = 1; i < calcs.size(); i++){
				Calculation current = calcs.get(i);
				Calculation left = calcs.get(i-1);
				
				if(current instanceof Add || current instanceof Subtract){
					Operator operator = (Operator) current;
					if(operator.getPart1() != null) continue;
					
					operator.setPart1(left);
					calcs.remove(i-1);
					gotOne = true;
					break;
				}
				
			}
		}
		
		//Should be only one left!
		return calcs.get(0);
	}
	
}
