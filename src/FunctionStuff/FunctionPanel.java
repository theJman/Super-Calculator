package FunctionStuff;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import BasicCalculator.CalcTextField;
import BasicCalculator.CalculatorManager;


public class FunctionPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton btnChange = new JButton("Save");
	private JButton btnSolve = new JButton("Solve");
	
	private JLabel lblFormula = new JLabel("Formula:");
	private JLabel lblName = new JLabel("Name:");
	private JLabel lblArgs = new JLabel("Arguments:");
	private JTextArea lblHowTo;
	private JTextField tfArg1 = new JTextField("agr1");
	private JTextField tfArg2 = new JTextField("arg2");
	private JTextField tfArg3 = new JTextField("arg3");
	private JTextField tfArg4 = new JTextField("arg4");
	
	private JTextField tfFormula = new JTextField("");
	private JTextField tfName = new JTextField("");
	
	private Function funct;
	/**
	 * Creates a new FunctionPanel
	 * @param loc location of panel
	 * @param f Function for panel to display, null if creating a new function
	 * @param manager Calculator manager used to set displays
	 */
	public FunctionPanel(Point loc, Function f){
		funct = f;
		setBounds(loc.x, loc.y, 335, 210);
		setBorder(BorderFactory.createLineBorder(Color.black));
		setLayout(null);
		
		lblName.setBounds(5,5,50,20);
		add(lblName);
		lblFormula.setBounds(5, 35, 70, 20);
		add(lblFormula);
		
		tfName.setBounds(90, 5, 200, 20);
		add(tfName);
		tfFormula.setBounds(90, 35, 200, 20);
		add(tfFormula);
		btnSolve.setBounds(85, 170, 60, 30);
		btnSolve.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				solvePressed();	
			}
		});
		add(btnSolve);
		btnChange.setBounds(195, 170, 60, 30);
		btnChange.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				savePressed();
			}
		});
		
		//set bounds for args
		tfArg4.setBounds(240, 65, 50, 20);
		tfArg3.setBounds(190, 65, 50, 20);
		tfArg2.setBounds(140, 65, 50, 20);
		tfArg1.setBounds(90, 65, 50, 20);
		lblArgs.setBounds(5, 65, 90, 20);
		//make text area like a label
		lblHowTo = new JTextArea("To create a function enter the formula for it in the\nformula box. For the peramiters use the formant\n\"(1)\" for the first peramiter and \"(2)\" for the second\nand so on up to 4.\nEx. (1) * (2) = arg1 * arg2", 3, 10);
		lblHowTo.setEditable(false);
		lblHowTo.setOpaque(false);
		lblHowTo.setFocusable(false);
		lblHowTo.setCursor(null);
		lblHowTo.setBorder(BorderFactory.createLineBorder(Color.black));
		lblHowTo.setBounds(5, 65, 325, 85);
		
		//set specific function options
		changeFunction(funct);
		
		add(lblHowTo);
		add(btnChange);
		add(lblArgs);
		add(tfArg1);
		add(tfArg2);
		add(tfArg3);
		add(tfArg4);
	}
	/**
	 * Alters display to have attributes of the new function
	 * @param f the function to show, null to create a new one
	 */
	public void changeFunction(Function f){
		funct = f;
		if(f != null){
			tfName.setText(f.getName());
			tfFormula.setText(f.getFormula());
			lblArgs.setVisible(true);
			lblHowTo.setVisible(false);
			//init arg text boxes
			switch (f.getNumOfArgs()) {
			case 4:
				tfArg4.setVisible(true);
			case 3:
				tfArg3.setVisible(true);
			case 2:
				tfArg2.setVisible(true);
			case 1:
				tfArg1.setVisible(true);
				break;
			}
			btnSolve.setText("Solve");
		}
		else{
			tfName.setText("Function Name");
			tfFormula.setText("Formula for the function");
			tfArg1.setVisible(false);
			tfArg2.setVisible(false);
			tfArg3.setVisible(false);
			tfArg4.setVisible(false);
			lblArgs.setVisible(false);
			lblHowTo.setVisible(true);
			btnSolve.setText("Back");
		}
	}
	/**
	 * Saves the function entered by the user whether it be creating a new one or changing a current one
	 */
	private void savePressed(){
		CalculatorManager manager = CalculatorManager.getManager();
		System.out.println("name form "+tfName.getText()+" "+tfFormula.getText());
		if (funct == null){
			try {
				new Function(tfName.getText(), tfFormula.getText());
				System.out.println("Save successful");
				manager.display("Save successful for: " +tfName.getText(), true);
				//update all the menus if save was successful
				manager.getFrame().updateMenuBar();
				CalculatorManager.getManager().getFrame().showButtonPanel();
			} catch (Exception e) {
				if(manager != null)
					manager.error(e.getMessage(),true);
				e.printStackTrace();
			}
		}
		else{
			try {
				Function.getFunctions().remove(funct);
				//if name is empty dont save empty function
				if(!tfName.getText().isEmpty()){
					new Function(tfName.getText(), tfFormula.getText());
					manager.display("Save successful for: " +tfName.getText(), true);
				}
				else
					manager.display("Successfully deleted: " +funct.getName(), true);
				System.out.println("Save successful");
				//update all the menus if save was successful
				manager.getFrame().updateMenuBar();
				CalculatorManager.getManager().getFrame().showButtonPanel();

			} catch (Exception e) {
				if(manager != null)
					manager.error(e.getMessage(),true);
				e.printStackTrace();
			}
		}
		System.out.println(Function.getFunctions());
	}
	private void solvePressed(){
		//if creating a new function, the button functions as a back button
		if(funct != null){
			//add to textfield to be solved
			CalcTextField tf = CalculatorManager.getManager().getFrame().getTextField();
			switch (funct.getNumOfArgs()) {
			case 4:
				tf.setText(tf.getText()+""+funct.getName()+"("+tfArg1.getText()+","+tfArg2.getText()+","+tfArg3+","+tfArg4+")");
				break;
			case 3:
				tf.setText(tf.getText()+""+funct.getName()+"("+tfArg1.getText()+","+tfArg2.getText()+","+tfArg3+")");
				break;
			case 2:
				tf.setText(tf.getText()+""+funct.getName()+"("+tfArg1.getText()+","+tfArg2.getText()+")");
				break;
			case 1:
				tf.setText(tf.getText()+""+funct.getName()+"("+tfArg1.getText()+")");
				break;

			default:
				tf.setText(tf.getText()+""+funct.getName()+"("+")");
				break;
			}
		}
		CalculatorManager.getManager().getFrame().showButtonPanel();
	}
}
