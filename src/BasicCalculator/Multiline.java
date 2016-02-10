package BasicCalculator;

import javax.swing.Icon;

import java.lang.reflect.Array;
import java.util.Vector;

import javax.swing.JLabel;

public class Multiline extends JLabel {

	private static final long serialVersionUID = 1L;
	public final int AMOUNT_OF_LINES = 12;
	
	private Vector<String> lines = new Vector<String>(AMOUNT_OF_LINES);
	int front = 0;
	int offSet = 0;
	
	public Multiline() {
		for(int i = 0; i < AMOUNT_OF_LINES; i++){
			lines.add(" ");
		}
	}
	
	public void addLine(String newLine){
		if(newLine.length() == 0)return;
		if(offSet != 0){
			front -= offSet;
			offSet = 0;
		}
		newLine = newLine.replace("\n", "");
		front--;
		if(front < 0)
			front = AMOUNT_OF_LINES-1;
		lines.set(front, newLine);
		
		refresh();
	}

	private void refresh(){
		String str = "";
		for(int i = 0; i < lines.size(); i++){
			int pos = (i + front);
			if(pos >= lines.size()){
				pos -= lines.size();
			}
			System.out.println(lines.get(pos));

			str += lines.get(pos);
			str += "\n";
		}
		str = str.substring(0, str.length()-2);
		System.out.println("str: \n" + str);
		setText("");
		super.setText(convertToMultiline(str));
	}
	
	public void shift(boolean down){
		if(down){
			if(offSet <= 0)return;
			front--;
			offSet--;
			if(front == -1){
				front += AMOUNT_OF_LINES;
			}
		}
		else{
			front++;
			offSet++;
			front %= AMOUNT_OF_LINES;
		}
		refresh();
	}
	
	public String getTop(){
		return lines.get(front);
	}
	
	public static String convertToMultiline(String orig)
	{
	    return "<html>" + orig.replaceAll("\n", "<br>");
	}

}
