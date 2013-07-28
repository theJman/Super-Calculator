package MenuBar;
import java.awt.Color;

import javax.swing.JMenuBar;


public class CalcMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	/**
	 * Creates a new menu bar with a file, function solve and help menu
	 */
	public CalcMenuBar(){
		setBackground(Color.gray);
		
		add(new FileMenu());
		add(new FunctionMenu());
		add(new SolveMenu());
		add(new HelpMenu());
	}

	public void updateMenus(){
		removeAll();
		updateUI();
		
		add(new FileMenu());
		add(new FunctionMenu());
		add(new SolveMenu());
		add(new HelpMenu());
	}
}
