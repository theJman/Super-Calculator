package BasicCalculator;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import savable.Settings;

/**
 * @author JeremyLittel
 *
 */
public class CalculatorFrame extends JFrame {

	private static final long serialVersionUID = -5908008666274025806L;
	private static CalculatorFrame mainCalcFrame;
	private CalculatorPanel calcPanel;

	/**
	 * Creates a new Calculator Frame with a calc panel
	 */
	public CalculatorFrame(){
		//create frame
		setBounds(250, 150, 500, 200);
		setLayout(null);
		setTitle("Super Calculator");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		//setUndecorated(true);
		
		setResizable(false);		
		calcPanel = new CalculatorPanel(this);
		add(calcPanel);
		setVisible(true);
		//auto open and save on opening and closing the program
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(calcPanel != null && Settings.AUTOSAVE.get().equals("true")){
					calcPanel.getCalcMenuBar().autoSave();
				}
			}
			@Override
			public void windowOpened(WindowEvent e){
				if(calcPanel != null && Settings.AUTOSAVE.get().equals("true")){
					calcPanel.getCalcMenuBar().autoOpen();
				}
			}
		}); 
		System.out.println("Auto round: " + Settings.AUTOROUND.get());

	}
	private CalculatorPanel getCalcPanel(){
		return calcPanel;
	}
	
	/**
	 * 
	 * @return the calculator frame
	 */
	public static CalculatorFrame getFrame(){
		return mainCalcFrame;
	}
	/**
	 * 
	 * @return the calculator panel
	 */
	public static CalculatorPanel getPanel(){
		return getFrame().getCalcPanel();
	}
	/**
	 * Main
	 * @param args not used currently
	 */
	public static void main(String[] args){
		mainCalcFrame = new CalculatorFrame();
	}
}
