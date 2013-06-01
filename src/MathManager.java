import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class MathManager {
	private JTextField textField;
	private JLabel label;
	private HashMap<String,String> memDict;
	private String lastValue;
	private JFrame frame;
	private ArrayList<String> previousLines;
	private int currentLineIndex;
	public MathManager(JTextField tf, JLabel lbl, JFrame fr){
		textField = tf;
		label = lbl;
		memDict = new HashMap<String,String>();
		lastValue = "";
		frame = fr;
		previousLines = new ArrayList<String>();
		currentLineIndex = -1;
	}
	
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
			display("");
			
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
		
				second = Solver.solveString(second);
				memDict.put(first, second);
				System.out.println("Key: ["+first +"] value: ["+second+"]");
				setTextField("");
				display(original);
				return;
			}
			if(eqString.contains("mem")){
				String displayText = "";
				for (String key : memDict.keySet()) {
					displayText = displayText + " ["+key+"="+memDict.get(key)+"]";
				}
				display(displayText);
				return;
			}
			
			//check for variables
			for(String key : memDict.keySet()){
				while (eqString.contains(key)) {
					int index = eqString.indexOf(key);
					eqString = eqString.substring(0,index) + memDict.get(key) + eqString.substring(index+key.length());
					
				}
			}
			String answer = Solver.solveString(eqString);
			System.out.println("FINAL: "+answer);
			eqString = eqString + " = " + answer;
			setTextField("");
			lastValue = answer;
			if (!label.getText().contains("Error")) {
				display(original+" = "+answer);
			}	
			//store in previous lines
			previousLines.add(label.getText());
			currentLineIndex++;
			System.out.println("Current line: "+currentLineIndex);
		
		} catch (Exception e) {
			e.printStackTrace();
			error(e.getMessage());
		}
		updateWidth();
	}
	String stringSolver(String string){
		//check for variables and replace them with their numbers
		for(String key : memDict.keySet()){
			while (string.contains(key)) {
				int index = string.indexOf(key);
				string = string.substring(0,index) + memDict.get(key) + string.substring(index+key.length());
			}
		}
		//TODO finish
		
		return "";
	}
	
	/**
	 * moves display down or up 
	 * @param down true to move down, false to move up
	 */
	public void moveLine(boolean down){
		
		if(down && currentLineIndex > 0){
			currentLineIndex--;
			display(previousLines.get(currentLineIndex));
		}
		else if(!down && currentLineIndex < previousLines.size()-1){
			currentLineIndex++;
			display(previousLines.get(currentLineIndex));
		}
	}
	
	private void setTextField(String text){
		textField.setText(text);
	}
	private void error(String message){
		display("Error: "+message);
	}
	private void display(String message){
		updateWidth();
		label.setText(message);
	}
	private void extendside(int width){
		JFrame f = frame;
		f.setBounds(f.getLocation().x, f.getLocation().y, width, f.getHeight());
		JTextField tf = textField;
		JLabel l = label;
		tf.setBounds(tf.getLocation().x, tf.getLocation().y, f.getWidth()-20, tf.getHeight());
		l.setBounds(l.getLocation().x, l.getLocation().y, f.getWidth()-20, l.getHeight());

	}
	public void updateWidth(){
		String labeltext = textField.getText();
		if(labeltext.length() < label.getText().length())
			labeltext = label.getText();
		if(labeltext.length()>26)
		{
			int l = labeltext.length();
			int w = 300;
			while(l-26 > 0){
				w+=8;
				l--;
			}
			extendside(w);
		}
		else
			extendside(300);
	}
}
