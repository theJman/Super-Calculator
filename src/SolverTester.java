import static org.junit.Assert.*;

import org.junit.Test;


public class SolverTester {
	private final double delta = 0.0000001;
	
	@Test
	public void basicMathTest() {
		try {
			assertEquals(9, Double.parseDouble(Solver.solveString("3^2")), delta);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
	}
	@Test
	public void functionTest() {
		try {
			assertEquals(4, Double.parseDouble(Solver.solveString("sqrt(2+2)*2")), delta);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void parenTest() {
		//3+(2+2)
		try {
			assertEquals(11.0,Double.parseDouble(Solver.solveString("3+(2+2)*(1+1)*(3-2)")), delta);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void doubleParenTest() {
	
		try {
			assertEquals(20, Double.parseDouble(Solver.solveString("((2+2)(3+2))")), delta);
			assertEquals(52, Double.parseDouble(Solver.solveString("((2+2)(3+2)+(2*3))*(1+1)")), delta);

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}
