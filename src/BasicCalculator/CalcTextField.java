package BasicCalculator;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

import FunctionStuff.Function;

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
					System.out.println(text);
					for(Function funct : Function.getFunctions()){
						if(text.contains(funct.getName())){
							//System.out.println("index: "+text.indexOf(funct.getName()));
							if(text.indexOf(funct.getName()) == getCaretPosition()-(funct.getName().length())){
								if (e.getKeyCode() != KeyEvent.VK_BACK_SPACE){
									System.out.println("Just typed function name");
									//insert parens
									setText(text+"()");
									setCaretPosition(getCaretPosition()-1);
								}
								else{
									//delete end paren
									int index = text.indexOf(')', getCaretPosition()-1);
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
	 * Adds text to current textField and updates the width
	 * @param text text to add
	 */
	public void addText(String text){
		setText(getText()+text);
		CalculatorFrame.getPanel().updateWidth();
	}
}
