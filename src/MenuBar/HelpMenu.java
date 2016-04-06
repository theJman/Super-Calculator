package MenuBar;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
public class HelpMenu extends JMenu{
	public HelpMenu(){
		setText("Help");
		setBackground(Color.gray);
		JMenuItem about = new JMenuItem("About Me");
		about.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				showHelp();
			}
		});
		add(about);
	}
	public void showHelp(){
		JOptionPane.showMessageDialog(null, "Welcome to Super Calculator what the standard computer calculator should be! version 1.0\n" +
				"~Features~\n-supports all normal functions EX. + - * / % ect.\n-supports powers ex 2^2=4   2E2=2x10^2\n" +
				"-supports roots ex sqrt(4)=2    root(3,8)=cuberoot8=2\n-supports trig functions ex sin(pi)=0  asin(0)=pi\n" +
				"-supports logarithms ex log10=1 logb(2,10) = \"log base 2 of 10\"  lne=1\n-supports parentheses ex 2+((1+1)(8-6))=6  sqrt(2+2)=4\n" +
				"-smart text field\n-you can create variables and use them ex b=3 then \"b+1\" = 4    foo= (9-1)*2 then foo-1=15\n" +
				"-see all of your variables by typeing \"mem\"\n-a label that displays the previous calculation\n" +
				"-you can go back and view previous calculations with up/down arrow keys\n" +
				"-press enter with a blank text box and the function in the label will be put into the text box\n" +
				"-can create and use your own functions by using the Functions menu bar item \n" +
				"-typing in \"last\" will automaticly put your last answer into the box\n" +
				"-supports summations: format is \"sum(start,count,function)\" EX. sum(1,2, x^x) = 5\n" +
				"-supports lists in formant {1,2,3} EX. list = {1,2,3} (can only be used in summations currently)\n" +
				"-supports lists in summation functions: format is \"sum(list, function)\" EX. sum({1,2},x^x)=5\n" +
				"-lists can be used as arguments in functions\n-can save your functions and variables to a file", "About", JOptionPane.PLAIN_MESSAGE);
		
	
	}
}
