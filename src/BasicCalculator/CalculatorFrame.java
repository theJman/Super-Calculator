package BasicCalculator;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import FunctionStuff.Function;
import FunctionStuff.FunctionPanel;
import MenuBar.CalcMenuBar;

/**
 * @author JeremyLittel
 *
 */
public class CalculatorFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private JLabel label;
	
	private CalcMenuBar menuBar;
	private FunctionPanel functionPanel;
	private CalcTextField textField;
	private CalcButtonsPanel buttonPanel;
	/**
	 * Creates a new Calculator Frame
	 */
	public CalculatorFrame(){
		//create frame
		setBounds(250, 150, 350, 320);
		setLayout(null);
		setTitle("Super Calculator");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);		
		//create subviews
		menuBar = new CalcMenuBar();
		menuBar.setBounds(0, 0, 350, 20);
		label = new JLabel(" Welcome");
		label.setBounds(8, 53, 333, 18);
		label.setBorder(LineBorder.createBlackLineBorder());
		textField = new CalcTextField();
		textField.setBounds(5, 30, 340, 20);
		textField.setFocusable(true);
		buttonPanel = new CalcButtonsPanel();
		buttonPanel.setLocation(7, 80);
		add(buttonPanel);
		add(menuBar);
		//add(buttonPanel);
		add(textField);
		add(label);
		
		
		//create function panel
		try {
			//functionPanel = new FunctionPanel(new Point(7,80),new Function("", "(1)(2)(3)(4)"),CalculatorManager.getManager());
			functionPanel = new FunctionPanel(new Point(7,80), null);
			functionPanel.setVisible(false);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		add(functionPanel);
		
	}
	/**
	 * Opens the frame
	 */
	public void open(){

		setVisible(true);
	}
	
	/**
	 * 
	 * @return the displaylabel
	 */
	public JLabel getLabel(){
		return label;
	}
	/**
	 * 
	 * @return the editable textfield
	 */
	public CalcTextField getTextField(){
		return textField;
	}
	/**
	 * 
	 * @return the calcMenuBar
	 */
	public CalcMenuBar getCalcMenuBar(){
		return menuBar;
	}
	/**
	 * Shows the function panel with a specific function
	 * @param f function to show, null if creating a new function
	 */
	public void showFunctionPanel(Function f){
		buttonPanel.setVisible(false);
		functionPanel.setVisible(true);
		functionPanel.changeFunction(f);
	}
	/**
	 * Shows the botton panel and hides the function panel
	 */
	public void showButtonPanel(){
		buttonPanel.setVisible(true);
		functionPanel.setVisible(false);
	}
	/**
	 * Updates the menubar items
	 */
	public void updateMenuBar(){
		menuBar.updateMenus();		
	}
}
