package BasicCalculator;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import savable.UserFunction;
import savable.Settings;
import savable.Variable;

import MenuBar.CalcMenuBar;

public class CalculatorPanel extends JPanel {
	private static final long serialVersionUID = 4995424473712070286L;
	
	private HistoryPanel history;
	private CalcMenuBar menuBar;
	private FunctionPanel functionPanel;
	private CalcTextField textField;
	private CalcButtonsPanel buttonPanel;
	private CalculatorFrame frame;
	
	//used to log the last message so we don't show it again
	private String lastMessage = "";
	
	private String lastValue;
	
	/**
	 * Creates a new calculator panel with a calculator frame
	 * @param frm
	 */
	public CalculatorPanel(CalculatorFrame frm){
		//create frame
		setBounds(0, 0, 500, 200);
		setLayout(null);
		frame = frm;
		
		Font defaultFont = new Font(getFont().getName(),getFont().getStyle(),getFont().getSize()+5);
		setFont(defaultFont);
		//create subviews
		menuBar = new CalcMenuBar(this);
		menuBar.setBounds(0, 0, 500, 20);
		
		history = new HistoryPanel();
		history.addLine("Welcome to SuperCaluclator, for more info click 'Help'");
		history.setBounds(8, 55, 484, 120);
		history.setBorder(LineBorder.createBlackLineBorder());
		
		
		textField = new CalcTextField();
		textField.setBounds(-3, 17, 506, 40);
		textField.setFont(defaultFont);
		textField.setFocusable(true);
		buttonPanel = new CalcButtonsPanel(this);
		buttonPanel.setLocation(7, 120);
		add(menuBar);
		//add(buttonPanel);
		add(textField);
		add(history);
		
		
		//create function panel
		try {
			functionPanel = new FunctionPanel(new Point(8,73), null,this);
			functionPanel.setVisible(false);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		add(functionPanel);
		
		lastValue = null;
		
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
	public void showFunctionPanel(UserFunction f){
		buttonPanel.setVisible(false);
		history.setVisible(false);
		functionPanel.setVisible(true);
		frame.setBounds(frame.getBounds().x, frame.getBounds().y, frame.getBounds().width, 300);
		setBounds(0,0,frame.getBounds().width,frame.getBounds().height);
		functionPanel.changeFunction(f);
	}

	/**
	 * Hides the function panel and resets the window
	 */
	public void hideFunctionPanel(){
		history.setVisible(true);
		functionPanel.setVisible(false);
		frame.setBounds(frame.getBounds().x, frame.getBounds().y, frame.getBounds().width, 200);
		setBounds(0,0,frame.getBounds().width,frame.getBounds().height);
	}
	/**
	 * Updates the menubar items
	 */
	public void updateMenuBar(){
		menuBar.updateMenus();		
	}
	/**
	 * Updates the width of the label and textfield relitive to the size of the window
	 */
	public void updateWidth(){
		//get the actual width of text and set the width of everything else to match
		FontMetrics metrics = getGraphics().getFontMetrics();

		int textFieldDif = (textField.getWidth() - metrics.stringWidth(textField.getText())) - 50;
		
		if(textFieldDif <= 0){
			extendside(frame.getWidth() - textFieldDif);
		}
		if(frame.getWidth() > 350 && textFieldDif > 0){
			extendside(500);
		}
		
	}
	/**
	 * Sets everything up according to a new width
	 * @param width
	 */
	private void extendside(int width){
		JFrame f = frame;
		f.setBounds(f.getLocation().x, f.getLocation().y, width, f.getHeight());
		JTextField tf = textField;
		setBounds(getLocation().x, getLocation().y, width, getHeight());
		HistoryPanel l = history;
		tf.setBounds(tf.getLocation().x, tf.getLocation().y, f.getWidth()+6, tf.getHeight());
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
			String string = textField.getText();
			if(string.length() == 0)return;//unsolvable
			if(string.equals("save")){
				menuBar.autoSave();
			}
			if(string.equals("set")){
				Settings.AUTOROUND.set("4");
				return;
			}
			else if(string.equals("get")){
				System.out.println("Auto round: " + Settings.AUTOROUND.get());
				return;
			}
			//clear display
			//check to see if they want to remove a variable
			if(string.contains("remove")){
				if(string.indexOf("remove") != 0){
					throw new InvalidInputException("Key Word: \"remove\" must be used at the begining of line");
				}
				//remove all variables on that line
				Vector<String> keysToRemove = new Vector<String>();
				for(String key : Variable.getVariableNames()){
					while (string.contains(key)) {
						keysToRemove.add(key);
						int index = string.indexOf(key);
						//remove from line
						string = string.substring(0,index) +  string.substring(index+key.length());
					}
				}
				//remove the variables from the dict
				for(String key : keysToRemove){
					Variable.remove(key);
				}
				display("Removed variables: "+keysToRemove.toString(),true);
				textField.setText("");
				//update menus
				updateMenuBar();
				return;
			}
			/*
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
			*/
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
			
			//creating a new variable
			if (string.contains("=")) {
				int index = string.indexOf("=");
				String first = string.substring(0, index);
				String second = string.substring(index+1);
				if(!second.contains("{"))
					second = Solver.solveString(second);
				new Variable(first, second);
				System.out.println("Key: ["+first +"] value: ["+second+"]");
				setTextField("");
				display(original,false);
				updateMenuBar();
				return;
			}
			else if(string.contains("mem")){
				String displayText = "";
				//make sure there are variables to show
				if(!Variable.hasVars()){
					displayText = "No Variables";
					display(displayText, false);
					setTextField("");
					return;
				}
				for (String key : Variable.getVariableNames()) {
					displayText = displayText + " ["+key+"="+Variable.getValue(key)+"]";
				}
				display(displayText, false);
				setTextField("");
				return;
			}
			
			//calls solve method from solver class
			String answer = Solver.solveString(string);
			System.out.println("FINAL: "+answer);
			string = string + " = " + answer;
			setTextField("");
			//lastValue = answer;
			Variable.setLast(answer);
			history.setAnswer(answer);
			if (!history.getText().contains("Error")) {
				display(original+" = "+answer,false);
			}	
			
		
		} catch (Exception e) {
			e.printStackTrace();
			error(e.getMessage(),true);
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
	 * Call every time a key is pressed
	 */
	public void keyPressed(){
		history.setAnswer("...");
		AutoSolve.solve(textField.getText(), history.getAnsLabel());
	}
	
	/**
	 * moves display down or up 
	 * @param down true to move down, false to move up
	 */
	public void moveLine(boolean down){
	
		if(down){
			if(!history.shiftDown()){
				textField.restoreInput();
			}
		}
		else if(history.getText().contains("=")){
			
			history.shiftUp();
			//set the textfield 
			String toInsert = history.getUpperFront();
			if(toInsert.length() > 0 && toInsert.contains("=")){
				textField.setText(toInsert.substring(0,toInsert.indexOf("=")-1));
			}
		}
		
	}
	/**
	 * Displays a message to the user
	 * @param message message to show
	 * @param showDialog true if you want to show a pop up window
	 */
	public void display(String message,boolean showDialog){
		updateWidth();
		message = message.replace('\n', ' ');
		history.addLine(message);
		if(showDialog){
			if(message.equals(lastMessage)){
				lastMessage = "";
			}
			else{
				lastMessage = message;
				JOptionPane.showMessageDialog(frame, message, "Super Calculator", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		updateWidth();
	}
	/**
	 * Displays an error message in the label and displays an error dialog box
	 * @param message error message
	 * @param showDialog true to show error dialog
	 */
	public void error(String message, boolean showDialog){
		if(message.equals(lastMessage)){
			lastMessage = "";
		}
		else{
			lastMessage = message;
			JOptionPane.showMessageDialog(textField, "Error: "+message, "Error", JOptionPane.ERROR_MESSAGE);
		}
			
	}
	
}
