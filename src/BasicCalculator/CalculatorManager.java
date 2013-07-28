package BasicCalculator;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import FunctionStuff.Function;
import FunctionStuff.SaveFile;
import MenuBar.CalcMenuBar;


public class CalculatorManager {
	private static CalculatorManager currentManager;
	
	private static HashMap<String,String> memDict;
	public static HashMap<String,String> getMemDict(){
		return memDict;
	}
	private int currentLineIndex;
	private CalculatorFrame frame;
	private JLabel label;
	private String lastValue;
	private ArrayList<String> previousLines;
	private JTextField textField;
	
	/**
	 * Creates a new calculator manager that manages the calculator by creating the frame and housing major functions
	 */
	public CalculatorManager(){
		//create frame
		frame = new CalculatorFrame();
		textField = frame.getTextField();
		label = frame.getLabel();
		memDict = new HashMap<String,String>();
		lastValue = "";
		previousLines = new ArrayList<String>();
		currentLineIndex = -1;
		//open the window
		frame.open();
	}

	/**
	 * Displays a message to the user
	 * @param message message to show
	 * @param showDialog true if you want to show a pop up window
	 */
	public void display(String message,boolean showDialog){
		updateWidth();
		label.setText(message);
		if(showDialog)
			JOptionPane.showMessageDialog(frame, message, "Super Calculator", JOptionPane.INFORMATION_MESSAGE);
		updateWidth();
	}
	/**
	 * Displays an error message in the label and displays an error dialog box
	 * @param message error message
	 * @param showDialog true to show error dialog
	 */
	public void error(String message, boolean showDialog){
		display("Error: "+message,false);
		if(showDialog)
			JOptionPane.showMessageDialog(frame, "Error: "+message, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * 
	 * @return the current calculator frame
	 */
	public CalculatorFrame getFrame(){
		return frame;
	}
	/**
	 * 
	 * @return Save file of current state
	 */
	public SaveFile getSaveFile(){
		return new SaveFile(Function.getFunctions(), memDict);
		
	}
	/**
	 * moves display down or up 
	 * @param down true to move down, false to move up
	 */
	public void moveLine(boolean down){
		
		if(down && currentLineIndex > 0){
			currentLineIndex--;
			display(previousLines.get(currentLineIndex),false);
		}
		else if(!down && currentLineIndex < previousLines.size()-1){
			currentLineIndex++;
			display(previousLines.get(currentLineIndex),false);
		}
	}
	/**
	 * 
	 * @param text text to put in the text field
	 */
	public void setTextField(String text){
		textField.setText(text);
	}
	/**
	 * Appends text to the text field
	 * @param text 
	 */
	public void addToTextField(String text){
		textField.setText(textField.getText()+text);
	}
	/**
	 * Solves and displays answer to eq in textfield
	 */
	public void solve(){
		
		try {
			String eqString = textField.getText();
			//check for no input
			if(eqString.length() == 0){
				//check to see if user wants to insert a line
				if(currentLineIndex !=	previousLines.size()-1){
					textField.setText(label.getText().substring(0, label.getText().indexOf("=")));
					currentLineIndex =	previousLines.size()-1;
				}
				//check to see if the want the last answer added back in
				else{
					textField.setText(lastValue);

				}
				return;
			}
			//clear display
			display("",false);
			
			//replace "last" with last answer
			if(eqString.contains("last")){
				int index = eqString.indexOf("last");
				if(index != 0){
					eqString = eqString.substring(0,index) + lastValue + eqString.substring(index+4);
				}
				else{
					eqString = lastValue + eqString.substring(index+4);
				}
			}
			String original = new String(eqString);
			
			//remove spaces
			if(eqString.substring(eqString.length()-1).equals(" "))
				eqString = eqString.substring(0,eqString.length()-1);
			while(eqString.contains(" ")){
				
				int index = eqString.indexOf(" ");
				System.out.println(index);
				
				if(eqString.charAt(0) == ' ')
					eqString = eqString.substring(1);
				else
					eqString = eqString.substring(0,index) + eqString.substring(index+1);
			}
			System.out.println(eqString);
			if (eqString.contains("=")) {
				int index = eqString.indexOf("=");
				String first = eqString.substring(0, index);
				String second = eqString.substring(index+1);
		
				second = Solver.solveString(second, memDict);
				memDict.put(first, second);
				System.out.println("Key: ["+first +"] value: ["+second+"]");
				setTextField("");
				display(original,false);
				return;
			}
			if(eqString.contains("mem")){
				String displayText = "";
				for (String key : memDict.keySet()) {
					displayText = displayText + " ["+key+"="+memDict.get(key)+"]";
				}
				display(displayText, false);
				return;
			}
			//check to see if a function is present
			//
			/*
			for(Function f : Function.getFunctions()){
				while(eqString.contains(f.getName()+"(")){
					System.out.println("contains function-----start------");
					int beginIndex = eqString.indexOf('(', eqString.indexOf(f.getName()));
					int endIndex = eqString.indexOf(')', beginIndex);
					String args = eqString.substring(beginIndex+1, endIndex);
					String[] tempArgArray = args.split(",");
					if(f.getNumOfArgs() != tempArgArray.length){
						//incorrect amount of args
						throw new InvalidInputException("Invalid number of args for function:"+f.getName());
					}
					//correct num of args so replace function with the answer to it
					System.out.println("eqString to replace: "+eqString.substring(eqString.indexOf(f.getName()), endIndex+1));
					System.out.println(f.solveFunction(tempArgArray));
					eqString = eqString.substring(0, eqString.indexOf(f.getName())) +  f.solveFunction(tempArgArray) + eqString.substring(endIndex+1);
					System.out.println("final: "+ eqString);

					System.out.println("contains function\n-----end------");

				}
			}
			*/
			//calls solve method from solver class
			String answer = Solver.solveString(eqString,memDict);
			System.out.println("FINAL: "+answer);
			eqString = eqString + " = " + answer;
			setTextField("");
			lastValue = answer;
			if (!label.getText().contains("Error")) {
				display(original+" = "+answer,false);
			}	
			//store in previous lines
			previousLines.add(label.getText());
			currentLineIndex++;
			System.out.println("Current line: "+currentLineIndex);
		
		} catch (Exception e) {
			e.printStackTrace();
			error(e.getMessage(),false);
		}
		updateWidth();
	}
	/**
	 * Updates the width of the label and textfield relitive to the size of the window
	 */
	public void updateWidth(){
		
		String labeltext = textField.getText();
		if(labeltext.length() < label.getText().length())
			labeltext = label.getText();
		if(labeltext.length()>26)
		{
			int l = labeltext.length();
			int w = 350;
			while(l-35 > 0){
				w+=8;
				l--;
			}
			extendside(w);
		}
		else
			extendside(350);
		
	}
	private void extendside(int width){
		JFrame f = frame;
		f.setBounds(f.getLocation().x, f.getLocation().y, width, f.getHeight());
		JTextField tf = textField;
		JLabel l = label;
		tf.setBounds(tf.getLocation().x, tf.getLocation().y, f.getWidth()-11, tf.getHeight());
		l.setBounds(l.getLocation().x, l.getLocation().y, f.getWidth()-17, l.getHeight());
		//update menubar
		CalcMenuBar mBar = frame.getCalcMenuBar();
		mBar.setBounds(0, 0, f.getWidth(), 20);
	}
	
	
	//main method
	public static void main(String[] args){
		currentManager = new CalculatorManager();
	}
	/**
	 * 
	 * @return the current calculatorManager
	 */
	public static CalculatorManager getManager(){
		return currentManager;
	}
	
}
