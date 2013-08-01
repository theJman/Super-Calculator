package BasicCalculator;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

public class CalcTextField extends JTextField {
	private static final long serialVersionUID = -6095306387473105904L;
	
	public CalcTextField(){
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e){
				CalculatorPanel panel = CalculatorFrame.getPanel();
				panel.updateWidth();
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
				case 57:
					if(e.isShiftDown())
						leftParnPressed();
					break;
				default:
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
