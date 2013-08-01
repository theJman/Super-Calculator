package FunctionStuff;

import static org.junit.Assert.*;

import org.junit.Test;

public class FunctionTester {

	@Test
	public void basic()  {
		try{
			Function funct = new Function("add three", "(1)+(2)+(3)");
			assertEquals(6.0, Double.parseDouble(funct.solveFunction("1","2","3")), .000001);
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}
		System.out.println("-------------------------\n\n--------------------------");
		
	}
	
	@Test
	public void moderate(){
		Function funct;
		try {
			funct = new Function("quadratic", "(-(2)+sqrt((2)^2-4*(1)*(3)))/(2*(1))");
			assertEquals(1.0, Double.parseDouble(funct.solveFunction("1","-2","1")), .000001);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	
	}
}
