package BasicCalculator;
import java.util.TreeMap;

import javax.swing.JFrame;

/**
 * @author JeremyLittel
 *
 */
public class CalculatorFrame extends JFrame {

	private static final long serialVersionUID = -5908008666274025806L;
	private static CalculatorFrame mainCalcFrame;
	/*
	//values need to be sorted according to length of key
	private static TreeMap<String,String> memDict;
	public static TreeMap<String,String> getMemDict(){
		if(memDict == null)
			memDict = new TreeMap<String, String>(new MemDictComparator());
		return memDict;
	}
	public static void setMemDict(TreeMap<String,String> map){
		memDict = map;
	}
	*/
	private CalculatorPanel calcPanel;

	/**
	 * Creates a new Calculator Frame with a calc panel
	 */
	public CalculatorFrame(){
		//create frame
		setBounds(250, 150, 350, 320);
		setLayout(null);
		setTitle("Super Calculator");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);		
		calcPanel = new CalculatorPanel(this);
		add(calcPanel);
		
		setVisible(true);
	}
	private CalculatorPanel getCalcPanel(){
		return calcPanel;
	}
	
	public static CalculatorFrame getFrame(){
		return mainCalcFrame;
	}
	public static CalculatorPanel getPanel(){
		return getFrame().getCalcPanel();
	}
	public static void main(String[] args){
		mainCalcFrame = new CalculatorFrame();
	}
}
