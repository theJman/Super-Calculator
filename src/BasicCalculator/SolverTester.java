package BasicCalculator;
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
	public void summationTest(){
		try {
			assertEquals(5.0,Double.parseDouble(Solver.summation(1, 2, 1, "x^x")), delta);
			assertEquals(10.0 ,Double.parseDouble(Solver.summation(1, 4, 1, "x")) , delta);
			new Variable("list", "{2,8,10}");
			assertEquals(20.0, Double.parseDouble(Solver.summation(Variable.listToArray(Variable.getValue("list")), "x")),delta);
			new Variable("list2", "{2,4,8}");
			assertEquals(20.0, Double.parseDouble(Solver.summation(Variable.listToArray(Variable.getValue("list")), "x")),delta);
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
