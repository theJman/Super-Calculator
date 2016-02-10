package savable;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class FunctionTester {

	@Test
	public void basic()  {
		try{
			UserFunction funct = new UserFunction("add three", "(1)+(2)+(3)");
			ArrayList<String> test = new ArrayList<String>();
			test.add("1");
			test.add("2");
			test.add("3");

			assertEquals(6.0, Double.parseDouble(funct.eval(test)), .000001);
		}catch(Exception e){
			e.printStackTrace();
			fail();
		}
		System.out.println("-------------------------\n\n--------------------------");
		
	}
	
	@Test
	public void moderate(){
		UserFunction funct;
		try {
			funct = new UserFunction("quadratic", "(-(2)+sqrt((2)^2-4*(1)*(3)))/(2*(1))");
			ArrayList<String> test = new ArrayList<String>();
			test.add("1");
			test.add("-2");
			test.add("1");
			assertEquals(1.0, Double.parseDouble(funct.eval(test)), .000001);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	
	}
}
