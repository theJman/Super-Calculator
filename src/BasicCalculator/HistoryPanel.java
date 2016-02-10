package BasicCalculator;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class HistoryPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public final int LINES = 17;
	
	private ArrayList<JLabel> labels = new ArrayList<JLabel>(LINES);
	private Stack<String> lowerOverflow = new Stack<String>();
	private Stack<String> upperOverflow = new Stack<String>();
	private JLabel answerLabel = new JLabel();
	
	/**
	 * Creates a new history panel
	 */
	public HistoryPanel() {
		setLayout(null);
		Font ansLbFont = new Font(getFont().getName(),getFont().getStyle(),getFont().getSize()+8);
		answerLabel.setFont(ansLbFont);
		answerLabel.setBounds(2,2, 480, 40);
		answerLabel.setBorder(LineBorder.createBlackLineBorder());
		add(answerLabel);
		for(int i = 0; i < LINES; i++){
			JLabel temp = new JLabel("");
			temp.setBounds(2,i * 12 + 44, 480, 12);
			
			labels.add(temp);
			add(temp);

		}
	}
	
	public void setAnswer(String ans){
		answerLabel.setText(ans);
	}
	public JLabel getAnsLabel(){
		return answerLabel;
	}
	
	/**
	 * First resets and then adds a line to the top label
	 * @param line to add
	 */
	public void addLine(String line){
		reset();
		addLineToTopLabel(line);
	}
	
	/**
	 * Adds a line to the top label and shifts every thing else down
	 * @param line to add
	 */
	private void addLineToTopLabel(String line){
		//if full add to the overflow
		if(labels.get(LINES-1).getText().length() > 0){
			lowerOverflow.push(labels.get(LINES-1).getText());
		}
		//shift
		for(int i = LINES - 1; i > 0; i--){
			labels.get(i).setText(labels.get(i-1).getText());
		}
		//add the new line
		labels.get(0).setText(line);
	}
	
	/**
	 * Shifts everything up
	 * @return if it can shift again
	 */
	public boolean shiftUp(){
		//make sure we have something to shift
		String topText = labels.get(0).getText();
		if(topText.length() > 0){
			upperOverflow.push(topText);
			//shift up
			for(int i = 0; i < LINES-1; i++){
				labels.get(i).setText(labels.get(i+1).getText());
			}
			//pull from lower 
			if(lowerOverflow.empty()){
				labels.get(LINES-1).setText("");
			}
			else{
				labels.get(LINES-1).setText(lowerOverflow.pop());
			}
		}
		//check if we can shift again
		if(labels.get(0).getText().length() > 0)
			return true;
		else
			return false;
	}
	
	/**
	 * Shifts everything down
	 * @return if it can shift again
	 */
	public boolean shiftDown(){
		//make sure we still can shift down
		if(!upperOverflow.empty()){
			//can shift down 
			addLineToTopLabel(upperOverflow.pop());
		}
		else{
			return false;
		}
		
		//check to see if the upperoverflow is now empty
		if(upperOverflow.empty()){
			return false;
		}
		else{
			return true;
		}
		
	}
	
	/**
	 * Resets by shifting down until there is no upper overflow
	 */
	public void reset(){
		while(shiftDown()){
			//shift down
			;
		}
	}
	
	/**
	 * Gets the line above the top label if shifted up
	 * @return last line added to the upper overflow
	 */
	public String getUpperFront(){
		if(upperOverflow.empty()){
			return "";
		}
		else{
			return upperOverflow.peek();
		}
	}
	
	/**
	 * Get the text in the top label
	 * @return
	 */
	public String getText(){
		return labels.get(0).getText();
	}
}
