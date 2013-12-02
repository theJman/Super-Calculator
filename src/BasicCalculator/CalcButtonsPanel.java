package BasicCalculator;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JButton;

import savable.Variable;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CalcButtonsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CalculatorPanel calcPanel;
	/**
	 * Create the panel.
	 */
	public CalcButtonsPanel(CalculatorPanel panel) {
		setBounds(0, 0, 335, 210);
		setBorder(BorderFactory.createLineBorder(Color.black));
		setLayout(null);
		
		calcPanel = panel;
		JButton btnNewButton = new JButton("7");
		btnNewButton.setBounds(157, 35, 30, 25);
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calcPanel.addToTextField("7");
			}
		});
		add(btnNewButton);
		
		JButton button = new JButton("8");
		button.setBounds(199, 35, 30, 25);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calcPanel.addToTextField("8");
			}
		});
		add(button);
		
		JButton button_1 = new JButton("9");
		button_1.setBounds(241, 35, 30, 25);
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calcPanel.addToTextField("9");
			}
		});
		add(button_1);
		
		JButton button_2 = new JButton("/");
		button_2.setBounds(299, 35, 30, 25);
		button_2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calcPanel.addToTextField("/");
			}
		});
		add(button_2);
		
		JButton button_3 = new JButton("4");
		button_3.setBounds(157, 72, 30, 25);
		button_3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calcPanel.addToTextField("4");
			}
		});
		add(button_3);
		
		JButton button_4 = new JButton("5");
		button_4.setBounds(199, 72, 30, 25);
		button_4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calcPanel.addToTextField("5");
			}
		});
		add(button_4);
		
		JButton button_5 = new JButton("6");
		button_5.setBounds(241, 72, 30, 25);
		button_5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calcPanel.addToTextField("6");
			}
		});
		add(button_5);
		
		JButton button_6 = new JButton("1");
		button_6.setBounds(157, 109, 30, 25);
		button_6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calcPanel.addToTextField("1");
			}
		});
		add(button_6);
		
		JButton button_7 = new JButton("2");
		button_7.setBounds(199, 109, 30, 25);
		button_7.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calcPanel.addToTextField("2");
			}
		});
		add(button_7);
		
		JButton button_8 = new JButton("3");
		button_8.setBounds(241, 109, 30, 25);
		button_8.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calcPanel.addToTextField("3");
			}
		});
		add(button_8);
		
		JButton button_9 = new JButton("*");
		button_9.setBounds(299, 72, 30, 25);
		button_9.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calcPanel.addToTextField("*");
			}
		});
		add(button_9);
		
		JButton button_10 = new JButton("-");
		button_10.setBounds(299, 109, 30, 25);
		button_10.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calcPanel.addToTextField("-");
			}
		});
		add(button_10);
		
		JButton button_11 = new JButton("+");
		button_11.setBounds(299, 146, 30, 25);
		button_11.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calcPanel.addToTextField("+");
			}
		});
		add(button_11);
		
		JButton button_12 = new JButton("0");
		button_12.setBounds(199, 146, 30, 25);
		button_12.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calcPanel.addToTextField("0");
			}
		});
		add(button_12);
		
		JButton button_13 = new JButton(".");
		button_13.setBounds(157, 146, 30, 25);
		button_13.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calcPanel.addToTextField(".");
			}
		});
		add(button_13);
		
		JButton button_14 = new JButton("-");
		button_14.setBounds(241, 146, 30, 25);
		button_14.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calcPanel.addToTextField("-");
			}
		});
		add(button_14);
		
		JButton btnSolve = new JButton("Solve");
		btnSolve.setToolTipText("solve");
		btnSolve.setBounds(157, 183, 172, 25);
		btnSolve.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calcPanel.solve();
			}
		});
		add(btnSolve);
		
		JButton btnClear = new JButton("New Function");
		btnClear.setToolTipText("clear text field");
		btnClear.setBounds(0, 35, 139, 25);
		btnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calcPanel.showFunctionPanel(null);
			}
		});
		add(btnClear);
		
		JButton btnNewVar = new JButton("new var");
		btnNewVar.setToolTipText("Create a new variable");
		btnNewVar.setBounds(157, 4, 72, 25);
		btnNewVar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calcPanel.setTextField("(variableName) = (variableValue)");
			}
		});
		add(btnNewVar);
		
		JButton btnViewVars = new JButton("view vars");
		btnViewVars.setToolTipText("view current variables");
		btnViewVars.setBounds(241, 4, 88, 25);
		btnViewVars.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String displayText = "";
				if(!Variable.hasVars()){
					displayText = "No Variables";
				}
				else{
					for (String key : Variable.getVariableNames()) {
						displayText = displayText + " ["+key+"="+Variable.getValue(key)+"]";
					}
				}
				calcPanel.display(displayText, false);
			}
		});
		add(btnViewVars);
		
		JButton button_15 = new JButton("Clear");
		button_15.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				calcPanel.setTextField("");
			}
		});
		button_15.setToolTipText("clear text field");
		button_15.setBounds(0, 4, 139, 25);
		add(button_15);
		
		JButton btnViewSciFunctions = new JButton("Help");
		btnViewSciFunctions.setToolTipText("clear text field");
		btnViewSciFunctions.setBounds(0, 183, 139, 25);
		add(btnViewSciFunctions);
	}
}
