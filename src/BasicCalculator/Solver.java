package BasicCalculator;
import java.util.ArrayList;
import java.util.TreeMap;
import FunctionStuff.Function;


public class Solver {
	/**
	 * Solves a math function in the format of a string and returns a number in the format of a string
	 * @param string
	 * @return answer to the function
	 * @throws InvalidInputException
	 */
	public static String solveString(String string) throws InvalidInputException{
		TreeMap<String,String> memDict = CalculatorFrame.getMemDict();
		if(string == null || string.length() == 0)
			throw new InvalidInputException("Can not solve a blank string");
		
		
		System.out.println("Starting String: "+ string);
		//make sure number is in front of round
		if(string.indexOf("round") == 0)
			string = "1"+string;

		//replace variables 
		//replaces the longest ones first to avoid short variables breaking up long ones
		if(memDict != null && memDict.size() > 0){
			//check for variables
			for(String key : memDict.keySet()){
				while (string.contains(key)) {
					int index = string.indexOf(key);
					string = string.substring(0,index) + memDict.get(key) + string.substring(index+key.length());

				}
			}

		}
		
		//doesn't start with a number so add one
		if(string.charAt(0) != '-' &&
				string.charAt(0) != '.' &&
				!Character.isDigit(string.charAt(0)))
			string = "0+" + string;
		
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
			else if(index==0 && !Character.isLetter(string.charAt(index+1)))
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
		if(string.contains("sum(")){
			System.out.println("summation");
			//get index of closing paren
			int parenCount = 0;
			int commaCheck = 0;
			int lastIndex = -1;
			int openParenCount = 0;
			for(int i = string.indexOf("sum(");i<string.length();i++){
				if(string.charAt(i) == '('){
					parenCount++;
					openParenCount++;
				}
				else if(string.charAt(i) == ')'){
					parenCount--;
					if(parenCount == 0){
						lastIndex = i;
						break;
					}
				}
				else if(string.charAt(i) == ',')
					commaCheck++;
			}
			//make sure input is valid
			if(lastIndex == -1)
				throw new InvalidInputException("Check Parenthesis in summation function.");
			if(commaCheck != 2)
				throw new InvalidInputException("Summation function requires 3 arguments seperated by commas");
			//call summation method with arguments
			//get arguments
			int index1 = string.indexOf("sum(")+4;
			int index2 = string.indexOf(',', index1);
			String arg1 = string.substring(index1, index2);
			int temp = index1;
			index1=index2+1;
			index2=string.indexOf(',',index1);
			String arg2 = string.substring(index1, index2);
			index1=index2+1;
			index2=string.indexOf(')',index1);
			String arg3 = string.substring(index1, index2);
			
			int start,count;
			try{
				start = Integer.parseInt(arg1);
				count = Integer.parseInt(arg2);
			}
			catch(Exception e){
				throw new InvalidInputException("First two arguments in summation function must be numbers.");
			}
			
			string = string.substring(0, string.indexOf("sum(")) + summation(start, count, 1, arg3)+string.substring(1+string.indexOf(')', string.indexOf("sum(")));
		}
		//
		//
		//
		//check for functions
		//
		//
		for(Function f : Function.getFunctions()){
			while(string.contains(f.getName()+"(")){
				System.out.println("contains function-----start------");
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
				String[] tempArgArray = args.split(",");
				if(f.getNumOfArgs() != tempArgArray.length){
					//incorrect amount of args
					throw new InvalidInputException("Invalid number of args for function:"+f.getName());
				}
				//correct num of args so replace function with the answer to it
				System.out.println("string to replace: "+string.substring(string.indexOf(f.getName()), endIndex+1));
				System.out.println(f.solveFunction(tempArgArray));
				string = string.substring(0, string.indexOf(f.getName())) +  f.solveFunction(tempArgArray) + string.substring(endIndex+1);
				System.out.println("final: "+ string);

				System.out.println("contains function\n-----end------");

			}
		}
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
			if(startCount != endCount){
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
		//check to see if they are using a function
		for(int i = 0;i<string.length();i++){
			if(string.charAt(i) == '(' )
				if(Character.isLetter(string.charAt(i+1))){
					for(int b = i+1;b<string.length();b++){
						if(!Character.isLetter(string.charAt(b))){
							
						}
					}
				}
		}
		
		
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
		//functions
		while(signs.contains("^ln")){
			int index = signs.indexOf("^ln");
			double num =  Math.pow(numbers.get(index), Math.log(numbers.get(index+1)));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("^log")){
			int index = signs.indexOf("^log");
			double num =  Math.pow(numbers.get(index), Math.log10(numbers.get(index+1)));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("^sqrt")){
			int index = signs.indexOf("^sqrt");
			if(numbers.get(index+1) < 0)
				return "Imaginary";
			double num =  Math.pow(numbers.get(index), Math.sqrt(numbers.get(index+1)));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("^sin")){
			int index = signs.indexOf("^sin");
			double num =  Math.pow(numbers.get(index), Math.sin(numbers.get(index+1)));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("^cos")){
			int index = signs.indexOf("^cos");
			double num =  Math.pow(numbers.get(index), Math.cos(numbers.get(index+1)));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("^tan")){
			int index = signs.indexOf("^tan");
			double num =  Math.pow(numbers.get(index), Math.tan(numbers.get(index+1)));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("^asin")){
			int index = signs.indexOf("^asin");
			double num =  Math.pow(numbers.get(index), Math.asin(numbers.get(index+1)));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("^acos")){
			int index = signs.indexOf("^acos");
			double num = Math.pow(numbers.get(index), Math.acos(numbers.get(index+1)));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("^atan")){
			int index = signs.indexOf("^atan");
			double num = Math.pow(numbers.get(index), Math.atan(numbers.get(index+1)));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		
		while(signs.contains("*ln")){
			int index = signs.indexOf("*ln");
			double num = numbers.get(index) * Math.log(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("*log")){
			int index = signs.indexOf("*log");
			double num = numbers.get(index) * Math.log10(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("*sqrt")){
			int index = signs.indexOf("*sqrt");
			if(numbers.get(index+1) < 0)
				return "Imaginary";
			double num = numbers.get(index) * Math.sqrt(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("*sin")){
			int index = signs.indexOf("*sin");
			double num = numbers.get(index) * Math.sin(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("*cos")){
			int index = signs.indexOf("*cos");
			double num = numbers.get(index) * Math.cos(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("*tan")){
			int index = signs.indexOf("*tan");
			double num = numbers.get(index) * Math.tan(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("*asin")){
			int index = signs.indexOf("*asin");
			double num = numbers.get(index) * Math.asin(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("*acos")){
			int index = signs.indexOf("*acos");
			double num = numbers.get(index) * Math.acos(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("*atan")){
			int index = signs.indexOf("*atan");
			double num = numbers.get(index) * Math.atan(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("/ln")){
			int index = signs.indexOf("/ln");
			double num = numbers.get(index) / Math.log(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("/log")){
			int index = signs.indexOf("/log");
			double num = numbers.get(index) / Math.log10(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("/sqrt")){
			int index = signs.indexOf("/sqrt");
			if(numbers.get(index+1) < 0)
				return "Imaginary";
			double num = numbers.get(index) / Math.sqrt(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("/sin")){
			int index = signs.indexOf("/sin");
			double num = numbers.get(index) / Math.sin(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("/cos")){
			int index = signs.indexOf("/cos");
			double num = numbers.get(index) / Math.cos(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("/tan")){
			int index = signs.indexOf("/tan");
			double num = numbers.get(index) / Math.tan(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("/asin")){
			int index = signs.indexOf("/asin");
			double num = numbers.get(index) / Math.asin(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("/acos")){
			int index = signs.indexOf("/acos");
			double num = numbers.get(index) / Math.acos(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("/atan")){
			int index = signs.indexOf("/atan");
			double num = numbers.get(index) / Math.atan(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("+ln")){
			int index = signs.indexOf("+ln");
			double num = numbers.get(index) + Math.log(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("+log")){
			int index = signs.indexOf("+log");
			double num = numbers.get(index) + Math.log10(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("+sqrt")){
			int index = signs.indexOf("+sqrt");
			if(numbers.get(index+1) < 0)
				return "Imaginary";
			double num = numbers.get(index) + Math.sqrt(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("+sin")){
			int index = signs.indexOf("+sin");
			double num = numbers.get(index) + Math.sin(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("+cos")){
			int index = signs.indexOf("+cos");
			double num = numbers.get(index) + Math.cos(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("+tan")){
			int index = signs.indexOf("+tan");
			double num = numbers.get(index) + Math.tan(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("+asin")){
			int index = signs.indexOf("+asin");
			double num = numbers.get(index) + Math.asin(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("+acos")){
			int index = signs.indexOf("+acos");
			double num = numbers.get(index) + Math.acos(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("+atan")){
			int index = signs.indexOf("+atan");
			double num = numbers.get(index) + Math.atan(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("-ln")){
			int index = signs.indexOf("-ln");
			double num = numbers.get(index) - Math.log(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("-log")){
			int index = signs.indexOf("-log");
			double num = numbers.get(index) - Math.log10(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("-sqrt")){
			int index = signs.indexOf("-sqrt");
			if(numbers.get(index+1) < 0)
				return "Imaginary";
			double num = numbers.get(index) - Math.sqrt(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("-sin")){
			int index = signs.indexOf("-sin");
			double num = numbers.get(index) - Math.sin(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("-cos")){
			int index = signs.indexOf("-cos");
			double num = numbers.get(index) - Math.cos(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("-tan")){
			int index = signs.indexOf("-tan");
			double num = numbers.get(index) - Math.tan(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("-asin")){
			int index = signs.indexOf("-asin");
			double num = numbers.get(index) - Math.asin(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("-acos")){
			int index = signs.indexOf("-acos");
			double num = numbers.get(index) - Math.acos(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("-atan")){
			int index = signs.indexOf("-atan");
			double num = numbers.get(index) - Math.atan(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("%ln")){
			int index = signs.indexOf("%ln");
			double num = numbers.get(index) % Math.log(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("%log")){
			int index = signs.indexOf("%log");
			double num = numbers.get(index) % Math.log10(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("%sqrt")){
			int index = signs.indexOf("%sqrt");
			if(numbers.get(index+1) < 0)
				return "Imaginary";
			double num = numbers.get(index) % Math.sqrt(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("%sin")){
			int index = signs.indexOf("%sin");
			double num = numbers.get(index) % Math.sin(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("%cos")){
			int index = signs.indexOf("%cos");
			double num = numbers.get(index) % Math.cos(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("%tan")){
			int index = signs.indexOf("%tan");
			double num = numbers.get(index) % Math.tan(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("%asin")){
			int index = signs.indexOf("%asin");
			double num = numbers.get(index) % Math.asin(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("%acos")){
			int index = signs.indexOf("%acos");
			double num = numbers.get(index) % Math.acos(numbers.get(index+1));
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		while(signs.contains("%atan")){
			int index = signs.indexOf("%atan");
			double num = numbers.get(index) % Math.atan(numbers.get(index+1));
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
		
		//multiplication
		while(signs.contains("*")){
			int index = signs.indexOf("*");
			double num = numbers.get(index) * numbers.get(index+1);
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		
	
		//division 
		while(signs.contains("/")){
			int index = signs.indexOf("/");
			double num = numbers.get(index) / numbers.get(index+1);
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
		
	
		//addition
		while(signs.contains("+")){
			int index = signs.indexOf("+");
			double num = numbers.get(index) + numbers.get(index+1);
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
			
		}
	
	
		//subtraction
		while(signs.contains("-")){
			int index = signs.indexOf("-");
			double num = numbers.get(index) - numbers.get(index+1);
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
		//rounding
		while(signs.contains("round")){
			int index = signs.indexOf("round");
			int place = (int) (double)numbers.get(index);
			if(place == 0)place = 1;
			double num = numbers.get(index+1);
			num *= place;
			num = Math.round(num);
			num /= place;
			signs.remove(index);
			numbers.remove(index);
			numbers.remove(index);
			numbers.add(index, num);
		}
		
		//check to see if there are still signs left if so then the sign in not supported
		if(signs.size()>0)
			throw new InvalidInputException("These operators are not supported: "+signs);
		
		
		System.out.println("Solved to get: " +string);
	
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
	 * Preforms a summation
	 * @param start start number
	 * @param count amount of additions
	 * @param incement
	 * @param string formula in whitch the letter 'x' will be replaced with the number
	 * @return
	 * @throws InvalidInputException
	 */
	public static String summation(int start, int count,double incement,final String string) throws InvalidInputException{
		double number = 0;
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
			number += Double.parseDouble(solveString(temp));
		}
		return ""+number;
	}
}
	