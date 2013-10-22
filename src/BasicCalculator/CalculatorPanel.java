package BasicCalculator;

import java.awt.Point;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import FunctionStuff.Function;
import FunctionStuff.FunctionPanel;
import MenuBar.CalcMenuBar;

public class CalculatorPanel extends JPanel {
	private static final long serialVersionUID = 4995424473712070286L;
	
	private JLabel label;
	private CalcMenuBar menuBar;
	private FunctionPanel functionPanel;
	private CalcTextField textField;
	private CalcButtonsPanel buttonPanel;
	private CalculatorFrame frame;
	//stuff for navigating previous commands 
	private int currentLineIndex;
	private ArrayList<String> previousLines;
	private String lastValue;
	
	/**
	 * Creates a new calculator panel with a calculator frame
	 * @param frm
	 */
	public CalculatorPanel(CalculatorFrame frm){
		//create frame
		setBounds(0, 0, 350, 320);
		setLayout(null);
		frame = frm;
		//create subviews
		menuBar = new CalcMenuBar(this);
		menuBar.setBounds(0, 0, 350, 20);
		label = new JLabel(" Welcome");
		label.setBounds(8, 53, 333, 18);
		label.setBorder(LineBorder.createBlackLineBorder());
		textField = new CalcTextField();
		textField.setBounds(5, 30, 340, 20);
		textField.setFocusable(true);
		buttonPanel = new CalcButtonsPanel(this);
		buttonPanel.setLocation(7, 80);
		add(buttonPanel);
		add(menuBar);
		//add(buttonPanel);
		add(textField);
		add(label);
		
		
		//create function panel
		try {
			//functionPanel = new FunctionPanel(new Point(7,80),new Function("", "(1)(2)(3)(4)"),CalculatorManager.getManager());
			functionPanel = new FunctionPanel(new Point(7,80), null,this);
			functionPanel.setVisible(false);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		add(functionPanel);
		
		lastValue = null;
		previousLines = new ArrayList<String>();
		currentLineIndex = -1;
	}

	/**
	 * 
	 * @return the displaylabel
	 */
	public JLabel getLabel(){
		return label;
	}
	/**
	 * 
	 * @return the editable textfield
	 */
	public CalcTextField getTextField(){
		return textField;
	}
	/**
	 * 
	 * @return the calcMenuBar
	 */
	public CalcMenuBar getCalcMenuBar(){
		return menuBar;
	}
	/**
	 * Shows the function panel with a specific function
	 * @param f function to show, null if creating a new function
	 */
	public void showFunctionPanel(Function f){
		buttonPanel.setVisible(false);
		functionPanel.setVisible(true);
		functionPanel.changeFunction(f);
	}
	/**
	 * Shows the button panel and hides the function panel
	 */
	public void showButtonPanel(){
		buttonPanel.setVisible(true);
		functionPanel.setVisible(false);
	}
	/**
	 * Updates the menubar items
	 */
	public void updateMenuBar(){
		menuBar.updateFunctions();		
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
		CalcMenuBar mBar = menuBar;
		mBar.setBounds(0, 0, f.getWidth(), 20);
	}
	
	/**
	 * Solves and displays answer to eq in textfield
	 */
	public void solve(){
		
		try {
			TreeMap<String,String> memDict = frame.getMemDict();
			String string = textField.getText();
			//check for no input
			if(string.length() == 0){
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
			//check to see if they want to remove a variable
			if(string.contains("remove")){
				if(string.indexOf("remove") != 0){
					throw new InvalidInputException("Key Word: \"remove\" must be used at the begining of line");
				}
				//remove all variables on that line
				Vector<String> keysToRemove = new Vector<String>();
				for(String key : memDict.keySet()){
					while (string.contains(key)) {
						keysToRemove.add(key);
						int index = string.indexOf(key);
						//remove from line
						string = string.substring(0,index) +  string.substring(index+key.length());
					}
				}
				//remove the variables from the dict
				for(String key : keysToRemove){
					memDict.remove(key);
				}
				display("Removed variables: "+keysToRemove.toString(),true);
				textField.setText("");
				return;
			}
			//replace "last" with last answer
			if(string.contains("last")){
				if(lastValue == null)
					throw new InvalidInputException("No last value to use.");
				int index = string.indexOf("last");
				if(index != 0){
					string = string.substring(0,index) + lastValue + string.substring(index+4);
				}
				else{
					string = lastValue + string.substring(index+4);
				}
			}
			String original = new String(string);
			
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
			System.out.println(string);
			if (string.contains("=")) {
				int index = string.indexOf("=");
				String first = string.substring(0, index);
				String second = string.substring(index+1);
		
				second = Solver.solveString(second);
				memDict.put(first, second);
				System.out.println("Key: ["+first +"] value: ["+second+"]");
				setTextField("");
				display(original,false);
				return;
			}
			else if(string.contains("mem")){
				String displayText = "";
				for (String key : memDict.keySet()) {
					displayText = displayText + " ["+key+"="+memDict.get(key)+"]";
				}
				display(displayText, false);
				setTextField("");
				return;
			}
			//check to see if a function is present
			//
			/*
			for(Function f : Function.getFunctions()){
				while(string.contains(f.getName()+"(")){
					System.out.println("contains function-----start------");
					int beginIndex = string.indexOf('(', string.indexOf(f.getName()));
					int endIndex = string.indexOf(')', beginIndex);
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
			*/
			
			
			//calls solve method from solver class
			String answer = Solver.solveString(string);
			System.out.println("FINAL: "+answer);
			string = string + " = " + answer;
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
		textField.addText(text);
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
	
}
