package FunctionStuff;
import java.io.Serializable;
import java.util.TreeMap;
import java.util.Vector;

import BasicCalculator.CalculatorFrame;
import BasicCalculator.Variable;


public class SaveFile implements Serializable {
	private static final long serialVersionUID = 7251358295845702084L;

	private Vector<Function> functions;
	private TreeMap<String,String> variables;
	private TreeMap<String,String> settingsDict;
	/**
	 * Creates a new save file with a function list and memory dictionary 
	 * @param functs
	 * @param nVariables
	 */
	public SaveFile(Vector<Function> functs, TreeMap<String,String> nVariables){
		functions = functs;
		variables = nVariables;
		settingsDict = null;
	}
	
	/**
	 * Creates a new save file with a function list and memory dictionary 
	 * @param functs
	 * @param nVariables
	 * 
	 */
	public SaveFile(Vector<Function> functs, TreeMap<String,String> nVariables, TreeMap<String,String> settings){
		functions = functs;
		variables = nVariables;
		settingsDict = settings;
	}
	
	
	/**
	 * @return the functions
	 */
	public Vector<Function> getFunctions() {
		return functions;
	}
	/**
	 * @return the variables
	 */
	public TreeMap<String, String> getVariables() {
		return variables;
	}
	
	public TreeMap<String,String> getsSettingsDict(){
		return settingsDict;
	}
	public static SaveFile getSaveFile(){
		return new SaveFile(Function.getFunctions(),Variable.getVariables());
	}
}
