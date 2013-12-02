package MenuBar;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import BasicCalculator.CalculatorFrame;

import savable.Variable;

public class VariableMenu extends JMenu {
	private static final long serialVersionUID = 8530584956049059971L;

	/**
	 * Creates a new menu with a list of all of the variables
	 */
	public VariableMenu(){
		setText("Variables");
		setBackground(Color.gray);
		addVariables();
	}
	/**
	 * Creates a list of the variables
	 */
	private void addVariables(){
		//create a menu of the variables
				if(Variable.hasVars()){
					for(final String str : Variable.getVariableNames()){
						JMenuItem tempItem = new JMenuItem(str);
						add(tempItem);
						tempItem.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent arg0) {
								CalculatorFrame.getPanel().getTextField().addText(str);
							}
						});
					}
				}
				//there arn't any variables so let user know
				else{
					JMenuItem tempItem = new JMenuItem("No Variables");
					add(tempItem);
				}
	}
	/**
	 * Updates the variables
	 */
	public void updateVariables(){
		removeAll();
		updateUI();
		addVariables();
	}
}
