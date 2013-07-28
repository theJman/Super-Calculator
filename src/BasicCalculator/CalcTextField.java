package BasicCalculator;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

public class CalcTextField extends JTextField {

	public CalcTextField(){
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e){
				CalculatorManager.getManager().updateWidth();
				switch (e.getKeyCode()) {
				//enter
				case 10: 
					CalculatorManager.getManager().solve();
					break;
				//down
				case 40:
					CalculatorManager.getManager().moveLine(true);
					break;
				//up
				case 38:
					CalculatorManager.getManager().moveLine(false);
					break;
				default:
					break;
				}
			}
		});
	}
}
