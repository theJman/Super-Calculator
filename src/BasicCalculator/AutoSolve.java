package BasicCalculator;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class AutoSolve {
	
	private Timer timer;
	
	public AutoSolve(){
		timer = new Timer();
		
	}

	public void stop(){
		timer.cancel();
	}
	
	public void start(final JTextField tf, final JLabel label){
		timer.scheduleAtFixedRate(new TimerTask(){
			@Override
			public void run() {
				AutoSolve.solve(tf.getText(), label);
			}
		}, 0, 200);
	}
	
	public static boolean solve(String toSolve, JLabel retVal){
		try{
			String ret = Solver.solveString(toSolve);
			retVal.setText(ret);
			return true;
		}
		catch(Exception e){
			return false;
		}
	}
}
