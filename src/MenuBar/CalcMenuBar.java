package MenuBar;
import java.awt.Color;

import javax.swing.JMenuBar;

import BasicCalculator.CalculatorPanel;


public class CalcMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	FunctionMenu functionMenu;
	FileMenu fileMenu;
	CalculatorPanel panel;
	VariableMenu variableMenu;
	/**
	 * Creates a new menu bar with a file, function solve and help menu
	 */
	public CalcMenuBar(CalculatorPanel panel){
		setBackground(Color.gray);
		this.panel = panel;
		functionMenu = new FunctionMenu(panel);
		fileMenu = new FileMenu(this);
		variableMenu = new VariableMenu();
		add(new FileMenu(this));
		add(functionMenu);
		add(variableMenu);
		add(new HelpMenu());
	}
	public void updateFunctions(){
		functionMenu.updateFunctions();
	}
	/**
	 * Updates the menus after something has been added
	 */
	public void updateMenus(){
		functionMenu.updateFunctions();
		variableMenu.updateVariables();
	}
	/**
	 * See autosave in FileMenu
	 */
	public void autoSave(){
		fileMenu.autoSave();
	}
	/**
	 * See autoopen in FileMenu
	 */
	public void autoOpen(){
		fileMenu.autoOpen();
	}
}
