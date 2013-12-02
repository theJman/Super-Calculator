package BasicCalculator;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

import savable.Function;


public class CalcTextField extends JTextField {
	private static final long serialVersionUID = -6095306387473105904L;
	
	public CalcTextField(){
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e){
				CalculatorPanel panel = CalculatorFrame.getPanel();
				panel.updateWidth();
				//System.out.println("Key code: " + e.getKeyCode());
				switch (e.getKeyCode()) {
				//enter
				case 10: 
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
				case KeyEvent.VK_OPEN_BRACKET:
					//auto complete '{'
					if(e.isShiftDown()){
						leftBracketPressed();
					}
					break;
				//up
				case 38:
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
					for(Function funct : Function.getFunctions()){
						if(text.contains(funct.getName())){
							//System.out.println("index: "+text.indexOf(funct.getName()));
							if(text.indexOf(funct.getName()) == getCaretPosition()-(funct.getName().length())){
								//check to make sure it isn't part of another word
								int index = text.indexOf(funct.getName());
								//don't auto paren if it is
								if(index > 0 && Character.isLetter(text.charAt(index-1)))
									break;
								if (e.getKeyCode() != KeyEvent.VK_BACK_SPACE){
									System.out.println("Just typed function name");
									//insert parens
									caretPos += 2;
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
									setCaretPosition(caretPos-1);
								}
								else{
									//delete end paren
									index = text.indexOf(')', getCaretPosition()-1);
									System.out.println("index: "+index);
									if(index > -1){
										if(text.length() > index+1)
											text = text.substring(0, index) + text.substring(index+1);
										else
											text = text.substring(0, index);
										setText(text);
									}
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
					//check for common functions
					//
					//
					//check for asin
					else if(text.contains("asin") && text.indexOf("asin") == caretPos-3 && e.getKeyCode() != KeyEvent.VK_BACK_SPACE){
						//make sure it isn't part of another word
						int index = text.indexOf("asin");
						if(index > 0 && Character.isLetter(text.charAt(index-1)))
							break;
						setText(text.substring(0,text.indexOf("asin")+3)+"()");
						if(getCaretPosition() < text.length())
							setText(text.substring(0,text.indexOf("asin")+3)+"()"+text.substring(caretPos+1));
						else
							setText(text.substring(0,text.indexOf("asin")+3)+"()");
						setCaretPosition(caretPos+1);
					}
					//check for acos
					else if(text.contains("acos") && text.indexOf("acos") == caretPos-3 && e.getKeyCode() != KeyEvent.VK_BACK_SPACE){
						//make sure it isn't part of another word
						int index = text.indexOf("acos");
						if(index > 0 && Character.isLetter(text.charAt(index-1)))
							break;
						setText(text.substring(0,text.indexOf("acos")+3)+"()");
						if(getCaretPosition() < text.length())
							setText(text.substring(0,text.indexOf("acos")+3)+"()"+text.substring(caretPos+1));
						else
							setText(text.substring(0,text.indexOf("acos")+3)+"()");
						setCaretPosition(caretPos+1);
					}
					//check for atan
					else if(text.contains("atan") && text.indexOf("atan") == caretPos-3 && e.getKeyCode() != KeyEvent.VK_BACK_SPACE){
						//make sure it isn't part of another word
						int index = text.indexOf("atan");
						if(index > 0 && Character.isLetter(text.charAt(index-1)))
							break;
						setText(text.substring(0,text.indexOf("atan")+3)+"()");
						if(getCaretPosition() < text.length())
							setText(text.substring(0,text.indexOf("atan")+3)+"()"+text.substring(caretPos+1));
						else
							setText(text.substring(0,text.indexOf("atan")+3)+"()");
						setCaretPosition(caretPos+1);
					}
					//check for sin
					else if(text.contains("sin") && text.indexOf("sin") == caretPos-3 && e.getKeyCode() != KeyEvent.VK_BACK_SPACE){
						//make sure it isn't part of another word
						int index = text.indexOf("sin");
						if(index > 0 && Character.isLetter(text.charAt(index-1)))
							break;
						setText(text.substring(0,text.indexOf("sin")+3)+"()");
						if(getCaretPosition() < text.length())
							setText(text.substring(0,text.indexOf("sin")+3)+"()"+text.substring(caretPos+1));
						else
							setText(text.substring(0,text.indexOf("sin")+3)+"()");
						setCaretPosition(caretPos+1);
					}
					//check for cos
					else if(text.contains("cos") && text.indexOf("cos") == caretPos-3 && e.getKeyCode() != KeyEvent.VK_BACK_SPACE){
						//make sure it isn't part of another word
						int index = text.indexOf("cos");
						if(index > 0 && Character.isLetter(text.charAt(index-1)))
							break;
						setText(text.substring(0,text.indexOf("cos")+3)+"()");
						if(getCaretPosition() < text.length())
							setText(text.substring(0,text.indexOf("cos")+3)+"()"+text.substring(caretPos+1));
						else
							setText(text.substring(0,text.indexOf("cos")+3)+"()");
						setCaretPosition(caretPos+1);
					}
					//check for tan
					else if(text.contains("tan") && text.indexOf("tan") == caretPos-3 && e.getKeyCode() != KeyEvent.VK_BACK_SPACE){
						//make sure it isn't part of another word
						int index = text.indexOf("tan");
						if(index > 0 && Character.isLetter(text.charAt(index-1)))
							break;
						setText(text.substring(0,text.indexOf("tan")+3)+"()");
						if(getCaretPosition() < text.length())
							setText(text.substring(0,text.indexOf("tan")+3)+"()"+text.substring(caretPos+1));
						else
							setText(text.substring(0,text.indexOf("tan")+3)+"()");
						setCaretPosition(caretPos+1);
					}
					//check for ln
					else if(text.contains("ln") && text.indexOf("ln") == caretPos-3 && e.getKeyCode() != KeyEvent.VK_BACK_SPACE){
						//make sure it isn't part of another word
						int index = text.indexOf("ln");
						if(index > 0 && Character.isLetter(text.charAt(index-1)))
							break;
						setText(text.substring(0,text.indexOf("ln")+3)+"()");
						if(getCaretPosition() < text.length())
							setText(text.substring(0,text.indexOf("ln")+3)+"()"+text.substring(caretPos+1));
						else
							setText(text.substring(0,text.indexOf("ln")+3)+"()");
						setCaretPosition(caretPos+1);
					}
					//check for log
					else if(text.contains("log") && text.indexOf("log") == caretPos-3 && e.getKeyCode() != KeyEvent.VK_BACK_SPACE){
						//make sure it isn't part of another word
						int index = text.indexOf("log");
						if(index > 0 && Character.isLetter(text.charAt(index-1)))
							break;
						setText(text.substring(0,text.indexOf("log")+3)+"()");
						if(getCaretPosition() < text.length())
							setText(text.substring(0,text.indexOf("log")+3)+"()"+text.substring(caretPos+1));
						else
							setText(text.substring(0,text.indexOf("log")+3)+"()");
						setCaretPosition(caretPos+1);
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
}
