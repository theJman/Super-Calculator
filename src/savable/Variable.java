package savable;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

import BasicCalculator.CalculatorFrame;
import BasicCalculator.InvalidInputException;



public class Variable implements Serializable {
	private static final long serialVersionUID = -5875549222457743023L;
	
	//
	//
	//STATIC 
	//map variables
	private static HashMap<String,String> variables;
	
	
	/**
	 * Gets the map of all of the variables
	 * @return
	 */
	public static HashMap<String,String> getVariables(){
		
		return variables;
	}

	/**
	 * Gets the value for a variable key
	 * @param key variable to get 
	 * @return String[] of variable(length of 1 if var is just a number)
	 */
	public static String getValue(String key){
		for(String tempKey : variables.keySet()){
			if(tempKey.equals(key))
				return variables.get(key);
		}
		return null;
	}
	/**
	 * Sets the values of the variables and calls to update the menubar
	 * Use when opening from savefile
	 * @param nVariables
	 */
	public static void setList(HashMap<String,String> nVariables){
		variables = nVariables;
		//refresh the menubar
		CalculatorFrame.getPanel().updateMenuBar();
	}
	/**
	 * Changes a list into an array
	 * @param list
	 * @return
	 * @throws InvalidInputException
	 */
	public static String[] listToArray(String list) throws InvalidInputException{
		System.out.println("List: "+list);
		//take out parens
		while(list.charAt(0) == '(')
			list = list.substring(1);
		while(list.charAt(list.length()-1) == ')')
			list = list.substring(0, list.length()-1);
		System.out.println("New List: "+list);
		if(list.contains("{") && list.contains("}")){
			int start = list.indexOf('{'), stop = list.indexOf('}');
			if(start >= stop-1)
				throw new InvalidInputException();
			String temp = list.substring(start+1, stop);
			String[] values = temp.split(",");
			return values;
		}
		else
			throw new InvalidInputException("Expected a list, got: "+list);
	}
	/**
	 * Checks if there are any variables
	 * @return true if the list is not empty
	 */
	public static boolean hasVars(){
		if(variables != null && variables.keySet().size() > 0)
			return true;
		return false;
	}
	/**
	 * 
	 * @param key
	 * @throws InvalidInputException
	 */
	public static void remove(String key) throws InvalidInputException{
		if(!variables.containsKey(key))
			throw new InvalidInputException("The variable \""+key+"\" does not exist.");
		variables.remove(key);
	}
	/**
	 * A set of the variable names
	 * @return the set or null if it is empty
	 */
	public static Set<String> getVariableNames(){
		if(hasVars())
			return variables.keySet();
		else 
			return null;
	}
	
	/**
	 * Set a variable that coresponds to the last answer
	 * @param value
	 */
	public static void setLast(String value){
		if(variables == null){
			variables = new HashMap<String, String>();
		}
		variables.put("last", value);
	}

	//
	//
	//NON STATIC
	/**
	 * Checks if variable name is valid and if it is then it creates a new variable and adds it to the list
	 * @param key name of var
	 * @param value value of var
	 * @param type wether it is a number or a list
	 * @throws InvalidInputException invalid variable name
	 */
	public Variable(String key, String value) throws InvalidInputException{
		//make sure variable name isn't already being used by computer or as a function
		if(key.equals("e") || key.equals("sum") || key.equals("rand") || key.equals("sqrt") || key.equals("root") ||
				key.equals("ln") || key.equals("log")||key.equals("sin")|| key.equals("cos") || key.equals("tan") ||
				key.equals("acos") ||key.equals("asin")|| key.equals("atan") || key.equals("E") || key.equals("round") ||
				key.equals("last") || key.equals("mem") || key.equals("x")){
			throw new InvalidInputException("Variable name \""+key+"\" is already being used by the computer.");
		}
		for(UserFunction f : UserFunction.getFunctions()){
			if(f.getName().equals(key))
				throw new InvalidInputException("There is already a function with the name: "+key);
		}
		
		//create variable
		if(variables  == null)
			variables = new HashMap<String,String>();
		variables.put(key, value);
	}
	
	/**
	 * Comparator for the variable list
	 * @author JeremyLittel
	 */
	public class VariableComparator implements Serializable, Comparator<String> {
		private static final long serialVersionUID = -2919772686332469018L;
		public VariableComparator(){}
		/**
		 * sort by key length then by natural ordering if length is the same
		 */
		@Override
		public int compare(String o1, String o2) {
			if(o1.length() < o2.length())
				return 1;
			else if(o1.length() > o2.length())
				return -1;
			return o1.compareTo(o2);
		}

	}
}
