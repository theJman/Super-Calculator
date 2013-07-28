package FunctionStuff;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Vector;


public class SaveFile implements Serializable {
	private static final long serialVersionUID = 7251358295845702084L;

	private Vector<Function> functions;
	private HashMap<String,String> memDict;
	/**
	 * Creates a new save file with a function list and memory dictionary 
	 * @param functs
	 * @param mDict
	 */
	public SaveFile(Vector<Function> functs, HashMap<String,String> mDict){
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
	public HashMap<String, String> getMemDict() {
		return memDict;
	}
}
