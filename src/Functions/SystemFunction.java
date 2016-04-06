package Functions;

import java.util.ArrayList;

import BasicCalculator.InvalidInputException;
import BasicCalculator.Solver;

public abstract class SystemFunction implements NamedFunction {

	protected String name;
	protected int numOfArgs;
	
	public SystemFunction(String name, int cArgs){
		this.name = name;
		numOfArgs = cArgs;
	}
	public String getName(){
		return name;
	}
	public int getNumOfArgs(){
		return numOfArgs;
	}
	/*
	public String eval(ArrayList<String> args) throws InvalidInputException{
		throw new InvalidInputException("Method: "+name+" is not implemented.");
	}
	*/
	
	public void checkArgs(ArrayList<String> args) throws InvalidFunctionUseException{
		if(args.size() != numOfArgs)throw new InvalidFunctionUseException(name);
	}
	
	public static ArrayList<NamedFunction> getAll(){
		ArrayList<NamedFunction> functions = new ArrayList<NamedFunction>();
		
		functions.add(new SystemFunction("sqrt",1) {
			@Override
			public String eval(ArrayList<String> args) throws InvalidInputException {
				checkArgs(args);
				String ans = ""+Math.sqrt(Double.parseDouble(Solver.solveString(args.get(0))));
				return ans;
			}
		});
		
		functions.add(new SystemFunction("ln",1) {
			@Override
			public String eval(ArrayList<String> args) throws InvalidInputException {
				checkArgs(args);
				String ans = ""+Math.log(Double.parseDouble(Solver.solveString(args.get(0))));
				return ans;
			}
		});
		
		functions.add(new SystemFunction("log",1) {
			@Override
			public String eval(ArrayList<String> args) throws InvalidInputException {
				checkArgs(args);
				String ans = ""+Math.log10(Double.parseDouble(Solver.solveString(args.get(0))));
				return ans;
			}
		});
		
		functions.add(new SystemFunction("logb",2) {
			@Override
			public String eval(ArrayList<String> args) throws InvalidInputException {
				checkArgs(args);
				String ans = ""+Math.log(Double.parseDouble(Solver.solveString(args.get(0)))) / Math.log(Double.parseDouble(Solver.solveString(args.get(1))));
				return ans;
			}
		});
		
		
		functions.add(new SystemFunction("root",2) {
			@Override
			public String eval(ArrayList<String> args) throws InvalidInputException {
				checkArgs(args);
				String ans = ""+Math.pow(Double.parseDouble(Solver.solveString(args.get(1))),1.0 / Double.parseDouble(Solver.solveString(args.get(0))));
				return ans;
			}
		});
		
		functions.add(new SystemFunction("sin",1) {
			@Override
			public String eval(ArrayList<String> args) throws InvalidInputException {
				checkArgs(args);
				String ans = ""+Math.sin(Double.parseDouble(Solver.solveString(args.get(0))));
				return ans;
			}
		});
		
		functions.add(new SystemFunction("cos",1) {
			@Override
			public String eval(ArrayList<String> args) throws InvalidInputException {
				checkArgs(args);
				String ans = ""+Math.cos(Double.parseDouble(Solver.solveString(args.get(0))));
				return ans;
			}
		});
		
		functions.add(new SystemFunction("tan",1) {
			@Override
			public String eval(ArrayList<String> args) throws InvalidInputException {
				checkArgs(args);
				String ans = ""+Math.tan(Double.parseDouble(Solver.solveString(args.get(0))));
				return ans;
			}
		});
		
		functions.add(new SystemFunction("asin",1) {
			@Override
			public String eval(ArrayList<String> args) throws InvalidInputException {
				checkArgs(args);
				String ans = ""+Math.asin(Double.parseDouble(Solver.solveString(args.get(0))));
				return ans;
			}
		});

		functions.add(new SystemFunction("acos",1) {
			@Override
			public String eval(ArrayList<String> args) throws InvalidInputException {
				checkArgs(args);
				String ans = ""+Math.acos(Double.parseDouble(Solver.solveString(args.get(0))));
				return ans;
			}
		});
		functions.add(new SystemFunction("atan",1) {
			@Override
			public String eval(ArrayList<String> args) throws InvalidInputException {
				checkArgs(args);
				String ans = ""+Math.atan(Double.parseDouble(Solver.solveString(args.get(0))));
				return ans;
			}
		});
		
		functions.add(new SystemFunction("round",1) {
			@Override
			public String eval(ArrayList<String> args) throws InvalidInputException {
				checkArgs(args);
				String ans = ""+Math.round(Double.parseDouble(Solver.solveString(args.get(0))));
				return ans;
			}
		});
		
		
		
		return functions;
	}
	

}
