package MenuBar;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import BasicCalculator.CalculatorManager;
import FunctionStuff.Function;

public class FunctionMenu extends JMenu {
	
	public FunctionMenu(){
		setText("Functions");
		setBackground(Color.gray);
		
		//create a menu of the functions
		for(Function f : Function.getFunctions()){
			 JMenuItem tempItem = new JMenuItem(f.getName());
			 add(tempItem);
			 final Function tempF = f;
			 tempItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					CalculatorManager.getManager().getFrame().showFunctionPanel(tempF);
				}
			});
		}
		
		//create a add new button
		JMenuItem tempItem = new JMenuItem("New Function...");
		 add(tempItem);
		 tempItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				CalculatorManager.getManager().getFrame().showFunctionPanel(null);
				
			}
		});
	}

}
