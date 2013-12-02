package savable;
import java.io.Serializable;
import java.util.TreeMap;
import java.util.Vector;


public class SaveFile implements Serializable {
	private static final long serialVersionUID = 7251358295845702084L;

	private Vector<Function> functions;
	private TreeMap<String,String> variables;
	private Vector<String> settings;
	/**
	 * Creates a new save file with a function list, memory dictionary, and current settings used
	 * @param functs
	 * @param nVariables
	 */
	public SaveFile(Vector<Function> functs, TreeMap<String,String> nVariables){
		functions = functs;
		variables = nVariables;
		settings = new Vector<String>();
		for(Settings s : Settings.values()){
			System.out.println("vals: "+s.get());
			settings.add(s.get());
		}
	}
	
	/**
	 * Creates a new save file with a function list and memory dictionary 
	 * @param functs
	 * @param nVariables
	 * 
	 */
	public SaveFile(Vector<Function> functs, TreeMap<String,String> nVariables, Vector<String> nSettings){
		functions = functs;
		variables = nVariables;
		settings = nSettings;
		
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
	/**
	 * Sets the settings according to the ones saved
	 */
	public void setSettings(){
		int count = -1;
		System.out.println("settings vals: " + settings.toString());
		for(Settings s : Settings.values()){
			count++;
			if(count < settings.size())
				s.set(settings.get(count));
		}
	}
	/**
	 * 
	 * @return a file with all savable information
	 */
	public static SaveFile getSaveFile(){
		return new SaveFile(Function.getFunctions(),Variable.getVariables());
	}
}
