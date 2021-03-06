package savable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
import BasicCalculator.InvalidInputException;
import BasicCalculator.Solver;
import Functions.InvalidFunctionUseException;
import Functions.NamedFunction;
import Functions.SystemFunction;
/**
 * 
 * @author JeremyLittel
 */

public class UserFunction implements Serializable, Functions.NamedFunction{

	//static list of functions
	//
	//
	private static ArrayList<UserFunction> functions;
	
	private static final long serialVersionUID = 3477901669782017960L;
	/**
	 * 
	 * @return a list of the functions
	 */
	public static ArrayList<UserFunction> getFunctions(){
		
		if(functions == null){
			functions = new ArrayList<UserFunction>();
			//add example functions
			try {
				new UserFunction("add", "(A1)+(A2)");
				new UserFunction("subtract", "(A1)-(A2)");
				new UserFunction("multiply", "(A1)*(A2)");
				new UserFunction("divide", "(A1)/(A2)");
				new UserFunction("count", "sum((A1),1)");
				new UserFunction("mean", "sum((A1),x)/count((A1))");
				new UserFunction("stddev", "sqrt(sum((A1), (x-mean((A1)))^2)/(count((A1))-1))");
			} catch (InvalidInputException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//sort functions according to name
		Collections.sort(functions, new Comparator<UserFunction>() {
			@Override
			public int compare(UserFunction o1, UserFunction o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		return functions;
	}
	
	public static ArrayList<NamedFunction> getAllFunctions(){
		ArrayList<NamedFunction> retVal = SystemFunction.getAll();
		for(NamedFunction f : getFunctions()){
			retVal.add(f);
		}
		return retVal;
	}
	
	/**
	 * @param functions the functions to set
	 */
	public static void setFunctions(ArrayList<UserFunction> functions) {
		UserFunction.functions = functions;
	}
	/**
	 * Use to add a new function to the list of functions. This will check to make sure a duplicate function isn't being added
	 * @param newF function to add
	 * @throws InvalidInputException
	 */
	public static void addToFunctions(UserFunction newF) throws InvalidInputException{
		//check to make sure it isn't a duplicate
		System.out.println(functions);
		for(UserFunction f: getFunctions()){
			if(f.getName().equalsIgnoreCase(newF.getName())){
				System.out.println("funct name::"+f.getName());
				throw new InvalidInputException("You already have an function named that");
			}
		}
		getFunctions().add(newF);
	}
	//
	//
	//end of static functions
	
	
	//formula for the function
	private String formula;

	//name of the function
	private String name;
	
	//amount of args
	private int numOfArgs;
	/**
	 * Creates a new function
	 * @param formula String of the formula with arguments listed as "(1)" for the first arg and "(2)" for the second and so on
	 * @param numberOfArgs the amount of args that the user added to the formula, needs to between 1 and 4
	 * @throws InvalidInputException
	 */
	public UserFunction(String name, String formula) throws InvalidInputException{
		//make sure that the name and formula are not null
		if(name == null || name.length() == 0)
			throw new InvalidInputException("Please enter a name");
		if(formula == null || formula.length() == 0)
			throw new InvalidInputException("Please enter a formula");
		//check to make sure there are no spaces in the name
		if(name.contains(" "))
			throw new InvalidInputException("The name can't contain spaces");
		//take that spaces out of the formula
		while(formula.contains(" ")){
			int index = formula.indexOf(" ");
			System.out.println(index);
			
			if(formula.charAt(0) == ' ')
				formula = formula.substring(1);
			else
				formula = formula.substring(0,index) + formula.substring(index+1);
		}
		//figure out the number of args
		if(formula.contains("(A1)")){
			if(formula.contains("(A2)")){
				if(formula.contains("(A3)")){
					if(formula.contains("(A4)")){
						numOfArgs = 4;
					}
					else{
						numOfArgs = 3;
					}
				}
				else{
					numOfArgs = 2;
				}
			}
			else{
				numOfArgs = 1;
			}
		}
		else{
			numOfArgs = 0;
		}
		if(!formula.contains("sum")){
			//make sure formula works
			try {
				String stringToSolve = new String(formula);
				switch (numOfArgs) {
				case 4:
					stringToSolve = stringToSolve.replace("(A4)", "1");
				case 3:
					stringToSolve = stringToSolve.replace("(A3)", "1");
				case 2:
					stringToSolve = stringToSolve.replace("(A2)", "1");
				default:
					stringToSolve = stringToSolve.replace("(A1)", "1");
					break;
				}
				//make sure there are no errors when solving
				Solver.solveString(stringToSolve);
				
			} catch (InvalidInputException e) {
				//function didn't work so throw an exception
				throw e;
			}
		}
		
		//if the function is valid then init the variables
		this.name = name;
		this.formula = formula;
		//add this function to the list of functions
		addToFunctions(this);
		
	}
	/**
	 * @return the formula
	 */
	public String getFormula() {
		return formula;
	}
	

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the numOfArgs
	 */
	public int getNumOfArgs() {
		return numOfArgs;
	}

	/**
	 * @param formula the formula to set
	 */
	public void setFormula(String formula) {
		this.formula = formula;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param numOfArgs the numOfArgs to set
	 */
	public void setNumOfArgs(int numOfArgs) {
		this.numOfArgs = numOfArgs;
	}


	/**
	 * Evaluates the function
	 * @param args arguments to use in the function
	 * @return the result of the function
	 * @throws InvalidInputException 
	 * 
	 */
	@Override
	public String eval(ArrayList<String> args) throws InvalidInputException {
		if(args.size() != numOfArgs) throw new InvalidFunctionUseException(name);
		
		String stringToSolve = new String(formula);
		
		//add in args
		switch (numOfArgs) {
		case 4:
			while(stringToSolve.contains("(A4)"))
				stringToSolve = stringToSolve.replace("(A4)", "("+args.get(3)+")");
		case 3:
			while(stringToSolve.contains("(A3)"))
				stringToSolve = stringToSolve.replace("(A3)", "("+args.get(2)+")");	
		case 2:
			while(stringToSolve.contains("(A2)"))
				stringToSolve = stringToSolve.replace("(A2)", "("+args.get(1)+")");
		default:
			while(stringToSolve.contains("(A1)"))
				stringToSolve = stringToSolve.replace("(A1)", "("+args.get(0)+")");
			break;
		}
		System.out.println("with args: "+stringToSolve);
		
		return Solver.solveString(stringToSolve);
	}
	
}
