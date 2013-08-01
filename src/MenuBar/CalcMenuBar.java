package MenuBar;
import java.awt.Color;

import javax.swing.JMenuBar;

import BasicCalculator.CalculatorPanel;


public class CalcMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	FunctionMenu functionMenu;
	CalculatorPanel panel;
	/**
	 * Creates a new menu bar with a file, function solve and help menu
	 */
	public CalcMenuBar(CalculatorPanel panel){
		setBackground(Color.gray);
		this.panel = panel;
		functionMenu = new FunctionMenu(panel);
		add(new FileMenu(this));
		add(functionMenu);
		add(new SolveMenu());
		add(new HelpMenu());
	}
	public void updateFunctions(){
		functionMenu.updateFunctions();
	}
	public void updateMenus(){
		removeAll();
		updateUI();
		
		add(new FileMenu(this));
		add(new FunctionMenu(panel));
		add(new SolveMenu());
		add(new HelpMenu());
	}
}
