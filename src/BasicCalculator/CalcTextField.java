package BasicCalculator;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

import savable.UserFunction;
import Functions.*;


public class CalcTextField extends JTextField {
	private static final long serialVersionUID = -6095306387473105904L;
	
	//stored input in the case that they are putting something from history in the textfield
	private String tempInput = "";
	
	public CalcTextField(){
		setFocusTraversalKeysEnabled(false);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e){
				CalculatorPanel panel = CalculatorFrame.getPanel();
				panel.updateWidth();
				panel.keyPressed();
				System.out.println(e.getKeyCode());
				//System.out.println("Key code: " + e.getKeyCode());
				switch (e.getKeyCode()) {
				//enter
				case 10: 
					tempInput = "";
					panel.solve();
					break;
				//down
				case 40:
					
					panel.moveLine(true);
					
					break;
				case KeyEvent.VK_COMMA:
					int carretPos = getCaretPosition();
					String str = getText();
					//added extra comma, remove it
					if(carretPos < str.length()-1 && str.charAt(carretPos) == ','){
						setText(str.substring(0,carretPos)+str.substring(carretPos+1));	
						setCaretPosition(carretPos);
					}
					break;
				case KeyEvent.VK_TAB:
					carretPos = getCaretPosition();
					str = getText();
					if(e.isShiftDown()){
						//check to see if there is a comma behind and if so move to it
						for(int i = carretPos; i > 0; i--){
							if(str.charAt(i) == ','){
								setCaretPosition(i);
								break;
							}
						}
					}
					else{
						//check to see if there is a comma ahead and if so move to it
						for(int i = carretPos; i < str.length(); i++){
							if(str.charAt(i) == ','){
								setCaretPosition(i+1);
								break;
							}
						}
					}
					
					break;
				case KeyEvent.VK_OPEN_BRACKET:
					//auto complete '{'
					if(e.isShiftDown()){
						leftBracketPressed();
					}
					break;
				//up
				case 38:
					if(tempInput.length() == 0)
						tempInput = getText();
					panel.moveLine(false);
					break;
				
				//right paren
				case 48:
					if(e.isShiftDown()){
						//make sure an automatically inserted paren isn't being unused
						//count each
						int carretIndex = getCaretPosition();
						int lParen = 0, rParen = 0;
						for(int i = 0; i < getText().length(); i++){
							if(getText().charAt(i) == '(')
								lParen++;
							else if(getText().charAt(i) == ')')
								rParen++;
						}
						if(rParen == lParen+1 && getText().charAt(getText().length()-1) == ')'){
							setText(getText().substring(0, getText().length()-1));
							setCaretPosition(carretIndex);
						}
					}
					break;
				//left paren
				case 57:
					if(e.isShiftDown())
						leftParnPressed();
					break;
				default:
				//check if a function name was just completed
					String text = getText();
					int caretPos = getCaretPosition();
					for(NamedFunction funct : UserFunction.getAllFunctions()){
						if(text.contains(funct.getName())){
							//check to see if we are at the function name
							int index = text.indexOf(funct.getName(), caretPos-funct.getName().length());

							if(index == getCaretPosition()-(funct.getName().length())){
								//check to make sure it isn't part of another word
								
								//check to make sure that we haven't already inserted a paren
								if( index+funct.getName().length() < text.length() && text.charAt(index+funct.getName().length()) == '(')
									continue;
								
								//don't auto paren if it is
								if(index > 0 && Character.isLetter(text.charAt(index-1)))
									break;
								if (e.getKeyCode() != KeyEvent.VK_BACK_SPACE){
									System.out.println("Just typed function name");
									//insert parens
									caretPos += 1;
									switch (funct.getNumOfArgs()) {
									case 1:
										setText(text.substring(0, getCaretPosition())+"()"+text.substring(getCaretPosition()));
										break;
									case 2:
										setText(text.substring(0, getCaretPosition())+"(,)"+text.substring(getCaretPosition()));
										break;
									case 3:
										setText(text.substring(0, getCaretPosition())+"(,,)"+text.substring(getCaretPosition()));
										break;
									case 4:
										setText(text.substring(0, getCaretPosition())+"(,,,)"+text.substring(getCaretPosition()));
										break;

									default:
										setText(text.substring(0, getCaretPosition())+"()"+text.substring(getCaretPosition()));
										break;
									}
									setCaretPosition(caretPos);
								}
							}
						}
					}
					//check if they want to operate on last result
					if(text.length() == 1 &&  (e.getKeyChar() == '+' || e.getKeyChar() == '-' || e.getKeyChar() == '/' || e.getKeyChar() == '*' || e.getKeyChar() == '%')){
						text = "last"+text;
						setText(text);
					}
					//check if using a sum function
					else if(text.contains("sum") && text.indexOf("sum") == caretPos-3 && e.getKeyCode() != KeyEvent.VK_BACK_SPACE){
						//make sure it isn't part of another word
						int index = text.indexOf("sum");
						if(index > 0 && Character.isLetter(text.charAt(index-1)))
							break;
						setText(text.substring(0,text.indexOf("sum")+3)+"()");
						if(getCaretPosition() < text.length())
							setText(text.substring(0,text.indexOf("sum")+3)+"()"+text.substring(caretPos+1));
						else
							setText(text.substring(0,text.indexOf("sum")+3)+"()");
						setCaretPosition(caretPos+1);
					}
				}	
			}
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				super.keyPressed(e);
				
				switch(e.getKeyCode()){
				case KeyEvent.VK_BACK_SPACE:
				case KeyEvent.VK_DELETE:
					//delete end paren
					//find the correct paren and remove it
					String text = getText();
					if(getCaretPosition() > 0 && text.charAt(getCaretPosition()-1) == '(')
					{
						int count = 0;
						int indexToRemove = -1;
						for(int i = getCaretPosition(); i < text.length(); i++){
							if(text.charAt(i) == '(')
								count++;
							else if(text.charAt(i) == ')'){
								if(count == 0){
									indexToRemove = i;
									break;
								}
								else{
									count--;
								}
							}
						}
						if(indexToRemove != -1){
							System.out.println("need to remove");
							//check if we just have cammas in the parenthesis and if so delete them
							boolean shouldDeleteCommas = true;
							for(int i = getCaretPosition()+1; i < indexToRemove;i++){
								if(text.charAt(i) != ',')shouldDeleteCommas = false;
							}
							int startDelete = indexToRemove;
							if(shouldDeleteCommas)startDelete = getCaretPosition();
							//remove
							if(text.length() > indexToRemove+1)
								text = text.substring(0, startDelete) + text.substring(indexToRemove+1);
							else
								text = text.substring(0, startDelete);
							int tempCar = getCaretPosition();
							setText(text);
							setCaretPosition(tempCar);
						}
					}
					break;
				}
			}
		});
	}
	/**
	 * Method to auto complete parenthesis
	 */
	private void leftParnPressed(){
		System.out.println("leftparen");
		int carrotPosition = getCaretPosition();
		String str = getText();
		//make sure there is more open then close parens
		//())
		int parenCount = 0;
		for(int i = 0; i<str.length();i++){
			if(str.charAt(i) == '(')
				parenCount++;
			else if(str.charAt(i) == ')')
				parenCount--;
		}
		System.out.println("count:"+parenCount);
		if(parenCount >= 0){
			addText(")");
			setCaretPosition(carrotPosition);
		}
	}
	/**
	 * Method to auto complete brackets
	 */
	private void leftBracketPressed(){
		int carrotPosition = getCaretPosition();
		String str = getText();
		//make sure there is more open then closed brackets
		//())
		int bracketCount = 0;
		for(int i = 0; i<str.length();i++){
			if(str.charAt(i) == '{')
				bracketCount++;
			else if(str.charAt(i) == '}')
				bracketCount--;
		}
		System.out.println("bracket");

		if(bracketCount >= 0){
			System.out.println("autobracket");
			insertText("}",carrotPosition);
			setCaretPosition(carrotPosition);
		}
	}
	/**
	 * Adds text to current textField and updates the width
	 * @param text text to add
	 */
	public void addText(String text){
		setText(getText()+text);
		CalculatorFrame.getPanel().updateWidth();
	}
	/**
	 * Insets text at a position
	 * @param text to insert
	 * @param pos to insert text
	 */
	public void insertText(String text,int pos){
		if(pos < getText().length()+1)
			setText(getText().substring(0, pos)+text+getText().substring(pos));
	}
	
	/**
	 * Restores stored temp input
	 */
	public void restoreInput(){
		if(tempInput.length() > 0)
			setText(tempInput);
		tempInput = "";
	}
}
