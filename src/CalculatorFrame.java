import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 * 
 */

/**
 * @author JeremyLittel
 *
 */
public class CalculatorFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel buttonPanel;
	private JTextField textField;
	private JLabel label;
	
	private MathManager manager;
	
	public CalculatorFrame(){
		setBounds(250, 150, 300, 300);
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		buttonPanel = new JPanel(null);
		label = new JLabel(" Welcome");
		label.setBounds(8, 33, 284, 18);
		label.setBorder(LineBorder.createBlackLineBorder());
		buttonPanel.setBounds(25, 60, 275, 250);
		textField = new JTextField();
		textField.setBounds(5, 10, 290, 20);
		textField.setFocusable(true);
		manager = new MathManager(textField, label,this);
		
		//add buttons
		
		//row 1
		JButton temp = new JButton("7");
		temp.setBounds(70, 5, 40, 40);
		temp.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e){
				textField.setText(textField.getText() + "7");
			}
		});
		buttonPanel.add(temp);
		temp = null;
		
		temp = new JButton("8");
		temp.setBounds(120, 5, 40, 40);
		temp.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e){
				textField.setText(textField.getText() + "8");
			}
		});
		buttonPanel.add(temp);
		temp = null;
		
		temp = new JButton("9");
		temp.setBounds(170, 5, 40, 40);
		temp.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e){
				textField.setText(textField.getText() + "9");
			}
		});
		buttonPanel.add(temp);
		temp = null;
		
		temp = new JButton("/");
		temp.setBounds(230, 5, 40, 40);
		temp.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e){
				textField.setText(textField.getText() + "/");
			}
		});
		buttonPanel.add(temp);
		temp = null;
		
		//row 2
		temp = new JButton("4");
		temp.setBounds(70, 55, 40, 40);
		temp.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e){
				textField.setText(textField.getText() + "4");
			}
		});
		buttonPanel.add(temp);
		temp = null;
		
		temp = new JButton("5");
		temp.setBounds(120, 55, 40, 40);
		temp.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e){
				textField.setText(textField.getText() + "5");
			}
		});
		buttonPanel.add(temp);
		temp = null;
		
		temp = new JButton("6");
		temp.setBounds(170, 55, 40, 40);
		temp.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e){
				textField.setText(textField.getText() + "6");
			}
		});
		buttonPanel.add(temp);
		temp = null;
		
		temp = new JButton("*");
		temp.setBounds(230, 55, 40, 40);
		temp.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e){
				textField.setText(textField.getText() + "*");
			}
		});
		buttonPanel.add(temp);
		temp = null;
		
		//row 3
		temp = new JButton("1");
		temp.setBounds(70, 105, 40, 40);
		temp.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e){
				textField.setText(textField.getText() + "1");
			}
		});
		buttonPanel.add(temp);
		temp = null;
		
		temp = new JButton("2");
		temp.setBounds(120, 105, 40, 40);
		temp.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e){
				textField.setText(textField.getText() + "2");
			}
		});
		buttonPanel.add(temp);
		temp = null;
		
		temp = new JButton("3");
		temp.setBounds(170, 105, 40, 40);
		temp.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e){
				textField.setText(textField.getText() + "3");
			}
		});
		buttonPanel.add(temp);
		temp = null;
		
		temp = new JButton("-");
		temp.setBounds(230, 105, 40, 40);
		temp.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e){
				textField.setText(textField.getText() + "-");
			}
		});
		buttonPanel.add(temp);
		temp = null;
		
		//row 4
		temp = new JButton(".");
		temp.setBounds(70, 155, 40, 40);
		temp.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e){
				textField.setText(textField.getText() + ".");
			}
		});
		buttonPanel.add(temp);
		temp = null;
		
		temp = new JButton("0");
		temp.setBounds(120, 155, 40, 40);
		temp.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e){
				textField.setText(textField.getText() + "0");
			}
		});
		buttonPanel.add(temp);
		temp = null;
		
		temp = new JButton("=");
		temp.setBounds(170, 155, 40, 40);
		temp.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e){
				solve();
			}
		});
		buttonPanel.add(temp);
		temp = null;
		
		temp = new JButton("+");
		temp.setBounds(230, 155, 40, 40);
		temp.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e){
				textField.setText(textField.getText() + "+");
			}
		});
		buttonPanel.add(temp);
		temp = null;
		
		add(buttonPanel);
		add(textField);
		add(label);
		/*
		textField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				solve();
			}
		});
		*/
		textField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e){
				manager.updateWidth();
				switch (e.getKeyCode()) {
				//enter
				case 10: 
					solve();
					break;
				//down
				case 40:
					manager.moveLine(true);
					break;
				//up
				case 38:
					manager.moveLine(false);
					break;
				default:
					break;
				}
			}
		});
		
	}
	public void open(){

		setVisible(true);
	}
	private void solve(){
		manager.solve();
	}
	
}
