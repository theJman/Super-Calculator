package BasicCalculator;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.JLabel;

import Functions.NamedFunction;
import Functions.SystemFunction;
import savable.UserFunction;
import savable.Variable;


/**
 * The "main brain" of the calculator.
 * This class contains handles the actual solving that the calculator does.
 * @author JeremyLittel
 *
 */
public class Solver {
	
	/**
	 * Solves a math function with functions in addition to the system functions
	 * @param string
	 * @return
	 * @throws InvalidInputException
	 */
	public static String solveStringWithAddedFunctions(String string, ArrayList<NamedFunction> functions) throws InvalidInputException{
		ArrayList<NamedFunction> funcs = SystemFunction.getAll();
		funcs.addAll(functions);
		return solveStringWithFunctions(string, funcs);
	}
	
	/**
	 * Solves a math function with just the system functions
	 * @param string
	 * @return
	 * @throws InvalidInputException
	 */
	public static String solveString(String string) throws InvalidInputException{
		return solveStringWithFunctions(string, SystemFunction.getAll());
	}
	
	/**
	 * Solves a math function in the format of a string and returns a number in the format of a string
	 * @param string
	 * @return answer to the function
	 * @throws InvalidInputException
	 */
	public static String solveStringWithFunctions(String string, ArrayList<NamedFunction> functions) throws InvalidInputException{
		if(string == null || string.length() == 0)
			throw new InvalidInputException("Can not solve a blank string");
		
		//remove spaces
		if(string.substring(string.length()-1).equals(" "))
			string = string.substring(0,string.length()-1);
		while(string.contains(" ")){
			
			int index = string.indexOf(" ");
			System.out.println(index);
			
			if(string.charAt(0) == ' ')
				string = string.substring(1);
			else
				string = string.substring(0,index) + string.substring(index+1);
		}
		
		//replace variables 
		//replaces the longest ones first to avoid short variables breaking up long ones
		if(Variable.hasVars()){
			//check for variables
			for(String key : Variable.getVariableNames()){
				while (string.contains(key)) {
					int index = string.indexOf(key);
					if(index > 0){	
						//make sure it isn't part of another word
						if(!Character.isLetter(string.charAt(index-1)))
							string = string.substring(0,index) + Variable.getValue(key) + string.substring(index+key.length());
						else
							break;
					}
					else if (index + key.length() +1 < string.length()){
						//make sure it isn't part of another word
						if(!Character.isLetter(string.charAt(index+key.length()+1)))
							string = string.substring(0,index) + Variable.getValue(key) + string.substring(index+key.length());
						else
							break;
					}
					else{
						string = string.substring(0,index) + Variable.getValue(key) + string.substring(index+key.length());
					}
				}
			}
		}
		
		//doesn't start with a number so add one
		/*
		if(string.charAt(0) != '-' &&
				string.charAt(0) != '.' &&
				!Character.isDigit(string.charAt(0)))
			string = "0+" + string;
			*/
		
		//make sure it can multiply numbers to parentheses ex. 2(2) = 4
		boolean doneChecking = false;
		while(!doneChecking){
			for(int i = 0; i < string.length(); i++){
				if(i < string.length()-2 && Character.isDigit(string.charAt(i)) && string.charAt(i+1) == '('){
					string = string.substring(0, i+1) + "*" + string.substring(i+1);
					break;
				}
				if(i < string.length()-1 && string.charAt(i) == ')' && Character.isDigit(string.charAt(i+1))){
					string = string.substring(0, i+1) + "*" + string.substring(i+1);
					break;
				}
				if(i == string.length()-1)
					doneChecking = true;
			}
		}
		
		//check for e
		int countE = 0;
		for(int i = 0; i<string.length();i++){
			if(string.charAt(i) == 'e')
				countE++;
		}
		//make sure the e isn't used in a word
		int lastEIndex = -1;
		for(int i = 0; i<=countE;i++){
			int index = string.indexOf('e', lastEIndex);
			lastEIndex = index;
			if(index == -1)
				continue;
			if(index>0 && !Character.isLetter(string.charAt(index-1)) && index<string.length()-2 && !Character.isLetter(string.charAt(index+1)))
				string = string.substring(0,index) + Math.E + string.substring(index+1);
			else if(index==0 && (string.length() == 1 || !Character.isLetter(string.charAt(index+1))))
				string = string.substring(0,index) + Math.E + string.substring(index+1);
			else if(index==string.length()-1 && !Character.isLetter(string.charAt(index-1)))
				string = string.substring(0,index) + Math.E + string.substring(index+1);

		}
		
		
		//check for rand
		while(string.contains("rand")){
			int index = string.indexOf("rand");
			string = string.substring(0,index) + Math.random() + string.substring(index+4);
		}
		//check for pi
		while(string.contains("pi")){
			int index = string.indexOf("pi");
			string = string.substring(0,index) + Math.PI + string.substring(index+2);
		}
		//check for summations
		//
		//
		while(string.contains("sum(")){
			System.out.println("summation");
			//get index of closing paren
			int parenCount = 0;
			int commaCheck = 0;
			int lastIndex = -1;
			int bracketCount = 0;
			for(int i = string.indexOf("sum("); i < string.length(); i++){
				switch (string.charAt(i)) {
				case '(':
					parenCount++;
					break;
				case '{':
					bracketCount++;
					break;
				case '}':
					bracketCount--;
					break;
				case ',':
					//only checks commas in sum function
					if(parenCount<2 && bracketCount==0)
						commaCheck++;
					break;
				}
				//break out if sum function is over
				if(string.charAt(i) == ')'){
					parenCount--;
					if(parenCount == 0){
						lastIndex = i;
						break;
					}
				}
			}
			//make sure input is valid
			if(bracketCount != 0)
				throw new InvalidInputException("Check your brackets.");
			if(lastIndex == -1)
				throw new InvalidInputException("Check Parenthesis in summation function.");
			if(commaCheck != 2 && commaCheck !=1)
				throw new InvalidInputException("Summation function requires 3 arguments seperated by commas");	
			//call summation method with arguments
			//get arguments
			int indexOfSum = string.indexOf("sum(");
			int index1 = indexOfSum + 4;
			//string that contains just the arguments
			String args = string.substring(index1, lastIndex+1);
			int argLastIndex = lastIndex - (string.substring(0, index1).length());
			System.out.println("char: "+ args.charAt(argLastIndex));
			index1 = 0;
			System.out.println("args: "+args);
			int fromIndex = index1;
			if(commaCheck==1 && args.contains("}")){
				fromIndex = args.indexOf('}');
			}
			int index2 = args.indexOf(',', fromIndex);
			System.out.println("from index: "+fromIndex+"\n"+args);
			String arg1 = args.substring(0, index2);
			String arg2 = "";
			//only need this arg for counter summation
			if(commaCheck == 2){
				index1=index2+1;
				index2=args.indexOf(',',index1);
				arg2 = args.substring(index1, index2);
			}
			
			index1=index2+1;
			index2=args.indexOf(')',index1);
			String arg3 = args.substring(index1, argLastIndex);
			//summation uses a list of numbers
			if(commaCheck == 1){
				System.out.println("arg1: "+arg1+"arg3: "+arg3);
				String[] list = Variable.listToArray(arg1);
				string = string.substring(0, indexOfSum) + summation(list, arg3)+string.substring(1+lastIndex);
				System.out.println("String after list sum: "+ string);
			}
			//summation uses counter
			else{
				int start,count;
				try{
					start = Integer.parseInt(arg1);
					count = Integer.parseInt(arg2);
				}
				catch(Exception e){
					throw new InvalidInputException("First two arguments in summation function must be numbers.");
				}	
				string = string.substring(0, string.indexOf("sum(")) + summation(start, count, 1, arg3)+string.substring(1+lastIndex);
				System.out.println("summation final: "+string);
			}	
		}
		
		//
		//
		//
		//check for functions
		//
		//
		boolean checkForFunctions = true;
		while(checkForFunctions){
			checkForFunctions = false;
			for(Functions.NamedFunction f : UserFunction.getAllFunctions()){
				while(string.contains(f.getName()+"(")){
					System.out.println("contains system function-----start------" + f.getName());
					//check to make sure the name isn't just part of a word
					int index = string.indexOf(f.getName());
					if(index > 0){
						if(Character.isLetter(string.charAt(index-1)))
							break;
					}
//					else if(index + f.getName().length() + 1 < string.length()){
//						if(Character.isLetter(string.charAt(index+f.getName().length()+1)))
//							break;
//					}
					int beginIndex = string.indexOf('(', string.indexOf(f.getName()));
					//count parenthesis
					//count will start at 1 because the first index is a start paren and finish when the count is back to 0 by subtraction by
					//each end paren
					int count = 0,endIndex = -1;
					for(int i = beginIndex; i<string.length();i++){
						if(string.charAt(i) == '(')
							count++;
						else if(string.charAt(i) == ')')
							count--;
						if(count == 0){
							//found the right index
							endIndex = i;
							//since we already found the index loop is pointless
							break;
						}
					}
					//make sure we found the right index
					if(endIndex == -1)
						throw new InvalidInputException("Check your parenthesis");
					String args = string.substring(beginIndex+1, endIndex);
					ArrayList<String> argsArray = new ArrayList<String>();
					
					int bracketCount = 0;
					int argCount = 1;
					for(int i = 0; i<args.length(); i++){
						switch (args.charAt(i)) {
						case '{':
						case '(':
							bracketCount++;
							break;
						case '}':
						case ')':
							bracketCount--;
							break;
						}
						
						//make sure it isn't a comma inside of a bracket
						if(args.charAt(i) == ',' && bracketCount == 0){
							argCount++;
							System.out.println("another");
						}
						else if(argsArray.size() < argCount){
							argsArray.add(""+args.charAt(i));
						}
						else{
							argsArray.set(argCount-1, argsArray.get(argCount-1)+args.charAt(i));
						}	
					}
					
					
					//correct num of args so replace function with the answer to it
					System.out.println("string to replace: "+string.substring(string.indexOf(f.getName()), endIndex+1));
					System.out.println(f.eval(argsArray));
					string = string.substring(0, string.indexOf(f.getName())) +  f.eval(argsArray) + string.substring(endIndex+1);
					System.out.println("final: "+ string);

					System.out.println("contains system function\n-----end------");
				}
			}
		}
		
		
		
		
		//
		//
		//
		//
		//Check for lists and treat them as vectors at this point and return a vector
		//take out all of the brackets and replace them with [] then switch them back after to avoid infinite loop  
		if(string.contains("{")){
			//shouldn't be any functions in the list at this point so we can split by comma
			int index = string.indexOf('{');
			int endIndex = string.indexOf('}');
			//check to make sure vector is valid
			int startcount=0, endcount=0;
			for(int i = 0; i<string.length();i++){
				switch (string.charAt(i)) {
				case '{':
					startcount++;
					break;
				case '}':
					endcount++;
					break;
				}
			}
			//start is either after or right befor end cant have an empty list ex. {}
			if(index >= endIndex-1)
				throw new InvalidInputException("Inavlid vector/list format");
			//too many starts and or ends
			if(startcount != 1 || endcount != 1)
				throw new InvalidInputException("Can only have one vector/list per function");
			String list = string.substring(index+1, endIndex);
			String[] listContents = list.split(",");
			String result = "{";
			if(listContents.length == 0)
				throw new InvalidInputException("Can't use empty vector");
			//solve the individual parts of the vector
			for(int i = 0; i < listContents.length; i++){
				String solveString = string.substring(0,index) + listContents[i] + string.substring(endIndex+1);
				if(i != 0)
					result += ",";
				result += solveString(solveString);
			}
			
			result += "}";
			return result;
		}
		//switch back to normal brackets 
		
		//
		//
		//
		//
		//
		
		//check for parentheses 
		if(string.contains("(")){
			//change to multiplication between parens
			while(string.contains(")(")){
				int index = string.indexOf(")(");
				string = string.substring(0, index+1) + "*" + string.substring(index+1);
			}
			int tempIndex = string.indexOf("(");

			int startCount = 0;
			int endCount = 0;
			//check to make sure they have input the right amount of parens
			for(int i = 0; i< string.length();i++){
				if(string.charAt(i) == '(')
					startCount++;
				else if(string.charAt(i) == ')')
					endCount++;
			}
			while(endCount>startCount){
				int index = string.lastIndexOf(')');
				if (index==-1)
					break;
				String temp = new String(string);
				string = temp.substring(0, index);
				if(index<temp.length()-1)
					string+=temp.substring(index+1);
				endCount--;
			}
			if(startCount != endCount){
				System.out.println("problem: "+string);
				throw new InvalidInputException("Check your parentheses");
			}
			int depthOfParen = 0;
			int startIndex = tempIndex;

			while(tempIndex < string.length() && tempIndex != -1){
			
				if(string.charAt(tempIndex) == '(')
					depthOfParen++;
				else if(string.charAt(tempIndex) == ')'){
					depthOfParen--;
				}
				if(depthOfParen==0){
					string = string.substring(0, startIndex)+solveString(string.substring(startIndex+1, tempIndex)) + string.substring(tempIndex+1);
					tempIndex = string.indexOf('(');
					startIndex = tempIndex;
					//System.out.println("String,index: "+string+" , "+tempIndex);
				}
				else
					tempIndex++;
			}


		}
		//should not be any parenthesis at this point
		//remove strays
		while(string.contains("(")){
			String temp = new String(string);
			string= temp.substring(0, temp.indexOf('('));
			if(temp.indexOf('(') < temp.length()-1)
				string = string + temp.substring(temp.indexOf('(')+1);
		}
		while(string.contains(")")){
			String temp = new String(string);
			string= temp.substring(0, temp.indexOf(')'));
			if(temp.indexOf(')') < temp.length()-1)
				string = string + temp.substring(temp.indexOf(')')+1);
		}
		//System.out.println("string before solve: "+string);
		
		ArrayList<Double> numbers = new ArrayList<Double>();
		ArrayList<String> signs = new ArrayList<String>();
		StringBuffer temp = new StringBuffer();
		boolean tempIsNumber = false;
		boolean isNegitive = false;
		//loop through all of the characters and fill the arrays with the numbers and the signs
		for(int i = 0; i < string.length();i++){
			//System.out.println("Char: "+string.charAt(i));
					//number character if:
					//string starts with a negitive
			if((i==0 && string.charAt(i) == '-') || 
					//it's a digit
					Character.isDigit(string.charAt(i)) ||
					//it's a decimal point
					string.charAt(i) == '.' || 
					//it is a negitive sign after anouther sign
					(i>1 && string.charAt(i) == '-' && !Character.isDigit(string.charAt(i-1)))){
				//temp was a sign to this point so put it into the signs array
				if(!tempIsNumber && i > 0){
					signs.add(temp.toString());
					temp = new StringBuffer();
				}
				if(string.charAt(i) == '-')
					isNegitive = true;
				else
					temp.append(string.charAt(i));
				tempIsNumber = true;
			}
			else{
				if(tempIsNumber){
					if(isNegitive){
						numbers.add(Double.parseDouble(temp.toString()) * -1);
						isNegitive = false;
					}
					else{
						numbers.add(Double.parseDouble(temp.toString()));
					}
					
					temp = new StringBuffer();
				}
				temp.append(string.charAt(i));
				tempIsNumber = false;
			}
		}
		//add last item
		if(tempIsNumber){
			if(isNegitive){
				numbers.add(Double.parseDouble(temp.toString()) * -1);
				isNegitive = false;
			}
			else{
				numbers.add(Double.parseDouble(temp.toString()));
			}
			
			temp = new StringBuffer();
		}
		else{
			signs.add(temp.toString());
			temp = new StringBuffer();
		}
		//print arrays
		//System.out.println(numbers);
		//System.out.println(signs);
		
		//make sure they have to correct amount of signs
		if(numbers.size() != 1 + signs.size()){
			System.out.println("problem: "+string);
			if(signs.size() == 1){
				throw new InvalidInputException("Invalid sign/variable/function: '"+signs.get(0)+"'");
			}
			throw new InvalidInputException();
		}
		//10 5 20
		//+ +
		//solve using PEMDAS
		while(signs.contains("root")){
			int index = signs.indexOf("root");
			double num =  Math.pow(numbers.get(index+1), 1/numbers.get(index)); 
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		//exponents
		while(signs.contains("^")){
			int index = signs.indexOf("^");
			double num = Math.pow(numbers.get(index),numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
	
		while(signs.contains("E")){
			int index = signs.indexOf("E");
			double num = numbers.get(index) * Math.pow(10,numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		
		//multiplication | division
		while(signs.contains("*") || signs.contains("/")){
			int index = 0;
			int multIndex = signs.indexOf("*");
			int divIndex = signs.indexOf("/");
			double num = 0.0;
			if(multIndex == -1 || (divIndex != -1 && divIndex < multIndex)){
				//divide
				index = divIndex;
				num = numbers.get(index) / numbers.get(index+1);
			}
			else{
				index = multIndex;
				num = numbers.get(index) * numbers.get(index+1);
			}			
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
		}
		
		//addition | subtraction
		while(signs.contains("+") || signs.contains("-")){
			int index = 0;
			int addIndex = signs.indexOf("+");
			int subIndex = signs.indexOf("-");
			double num = 0.0;
			if(addIndex == -1 || (subIndex != -1 && subIndex < addIndex)){
				//divide
				index = subIndex;
				num = numbers.get(index) - numbers.get(index+1);
			}
			else{
				index = addIndex;
				num = numbers.get(index) + numbers.get(index+1);
			}			
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
		}
		
		//remainder division
		while(signs.contains("%")){
			int index = signs.indexOf("%");
			double num = numbers.get(index) % numbers.get(index+1);
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		
		//check to see if there are still signs left if so then the sign in not supported
		if(signs.size()>0)
			throw new InvalidInputException("These operators are not supported: "+signs);
		
		
		//System.out.println("Solved to get: " +string);
	
		String retVal = "";
		if(numbers.get(0)%1 == 0){
			retVal = ""+Math.round(numbers.get(0));
		}
		else{
			retVal = ""+numbers.get(0);
		}
		return retVal;
	}
	/**
	 * Performs a summation
	 * @param start start number
	 * @param count amount of additions
	 * @param incement
	 * @param string formula in which the letter 'x' will be replaced with the number
	 * @return
	 * @throws InvalidInputException
	 */
	public static String summation(int start, int count,double incement,final String string) throws InvalidInputException{
		double number = 0;
		System.out.print("Solving");
		for(int i = start; i < start+count; i+=incement){
			//swap x if needed
			String temp = string;
			while(temp.contains("x")){
				int index = temp.indexOf('x');
				if(index == temp.length()-1)
					temp = temp.substring(0, index)+"("+i+")";
				else
					temp = temp.substring(0, index)+"("+i+")"+temp.substring(index+1);	
			}
			//System.out.println("Temp before solve: "+temp);
			//System.out.println("summation function"+temp);
			int dotCount = (int)count/10;
			if(i == dotCount+start)
				System.out.println("10%");
			else if (i== 2*dotCount+start)
				System.out.println("20%");
			else if (i== 3*dotCount+start)
				System.out.println("30%");
			else if (i== 4*dotCount+start)
				System.out.println("40%");
			else if (i== 5*dotCount+start)
				System.out.println("50%");
			else if (i== 6*dotCount+start)
				System.out.println("60%");
			else if (i== 7*dotCount+start)
				System.out.println("70%");
			else if (i== 8*dotCount+start)
				System.out.println("80%");
			else if (i== 9*dotCount+start)
				System.out.println("90%");
			else if (i== 10*dotCount+start-1)
				System.out.println("100%");

			try {
				number += Double.parseDouble(solveString(temp));
			} catch (Exception e) {
				System.out.println("ERROR: "+temp);
			}
		}
		return ""+number;
	}
	public static String summation(String[] list, final String string){
		double number = 0;
		System.out.print("Solving");
		
		int start =1,count = list.length;
		int i = 1;
		for(String str : list){
			//swap x if needed
			String temp = string;
			while(temp.contains("x")){
				int index = temp.indexOf('x');
				if(index == temp.length()-1)
					temp = temp.substring(0, index)+"("+str+")";
				else
					temp = temp.substring(0, index)+"("+str+")"+temp.substring(index+1);	
			}
			//System.out.println("Temp before solve: "+temp);
			//System.out.println("summation function"+temp);
			int dotCount = (int)count/10;
			if(i == dotCount+start)
				System.out.println("10%");
			else if (i== 2*dotCount+start)
				System.out.println("20%");
			else if (i== 3*dotCount+start)
				System.out.println("30%");
			else if (i== 4*dotCount+start)
				System.out.println("40%");
			else if (i== 5*dotCount+start)
				System.out.println("50%");
			else if (i== 6*dotCount+start)
				System.out.println("60%");
			else if (i== 7*dotCount+start)
				System.out.println("70%");
			else if (i== 8*dotCount+start)
				System.out.println("80%");
			else if (i== 9*dotCount+start)
				System.out.println("90%");
			else if (i== 10*dotCount+start-1)
				System.out.println("100%");
			
			try {
				number += Double.parseDouble(solveString(temp));
			} catch (Exception e) {
				System.out.println("ERROR: "+temp);
			}
			i++;
		}
		return ""+number;
	}
}
	