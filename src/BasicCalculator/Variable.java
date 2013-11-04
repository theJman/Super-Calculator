package BasicCalculator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeMap;

public class Variable {
	//
	//
	//STATIC 
	//map variables
	private static TreeMap<String,String> variables;
	public static TreeMap<String,String> getVariables(){
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
	 * Sets the values of the variables
	 * Use when opening from savefile
	 * @param nVariables
	 */
	public static void setList(TreeMap<String,String> nVariables){
		variables = nVariables;
	}
	/**
	 * Changes a list into an array
	 * @param list
	 * @return
	 * @throws InvalidInputException
	 */
	public static String[] listToArray(String list) throws InvalidInputException{
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
	public static boolean hasVars(){
		if(variables != null && variables.keySet().size() > 0)
			return true;
		return false;
	}
	public static void remove(String key) throws InvalidInputException{
		if(!variables.containsKey(key))
			throw new InvalidInputException("The variable \""+key+"\" does not exist.");
		variables.remove(key);
	}
	public static Set<String> getVars(){
		return variables.keySet();
	}

	//
	//
	//NON STATIC
	/**
	 * Creates a new variable and adds it to the list
	 * @param key name of var
	 * @param value value of var
	 * @param type wether it is a number or a list
	 * @throws InvalidInputException
	 */
	public Variable(String key, String value) throws InvalidInputException{
		//make sure variable doesn't already exist
		if(variables != null && getValue(key) != null)
			throw new InvalidInputException("This variable already exists.");
		//create variable
		if(variables  == null)
			variables = new TreeMap<String,String>(new VariableComparator());
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
