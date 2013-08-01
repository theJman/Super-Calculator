package MenuBar;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import BasicCalculator.CalculatorFrame;
import BasicCalculator.CalculatorPanel;
import FunctionStuff.Function;

public class FunctionMenu extends JMenu {
	private static final long serialVersionUID = 8587329656560955858L;
	CalculatorPanel panel;
	
	/**
	 * Creates a new function menu with all of the user created functions
	 */
	public FunctionMenu(CalculatorPanel panel){
		setText("Functions");
		setBackground(Color.gray);
		this.panel = panel;
		addFunctions();
		
		
	}
	private void addFunctions(){
		//create a menu of the functions
		for(Function f : Function.getFunctions()){
			JMenuItem tempItem = new JMenuItem(f.getName());
			add(tempItem);
			final Function tempF = f;
			tempItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					panel.showFunctionPanel(tempF);
				}
			});
		}
		//create a add new button
		JMenuItem tempItem = new JMenuItem("New Function...");
		add(tempItem);
		tempItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				panel.showFunctionPanel(null);

			}
		});
	}
	public void updateFunctions(){
		removeAll();
		updateUI();
		addFunctions();
	}
}
