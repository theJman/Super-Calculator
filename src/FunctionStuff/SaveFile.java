package FunctionStuff;
import java.io.Serializable;
import java.util.TreeMap;
import java.util.Vector;

import BasicCalculator.CalculatorFrame;


public class SaveFile implements Serializable {
	private static final long serialVersionUID = 7251358295845702084L;

	private Vector<Function> functions;
	private TreeMap<String,String> memDict;
	/**
	 * Creates a new save file with a function list and memory dictionary 
	 * @param functs
	 * @param mDict
	 */
	public SaveFile(Vector<Function> functs, TreeMap<String,String> mDict){
		functions = functs;
		memDict = mDict;
	}
	
	/**
	 * @return the functions
	 */
	public Vector<Function> getFunctions() {
		return functions;
	}
	/**
	 * @return the memDict
	 */
	public TreeMap<String, String> getMemDict() {
		return memDict;
	}
	public static SaveFile getSaveFile(){
		return new SaveFile(Function.getFunctions(),CalculatorFrame.getMemDict());
	}
}
