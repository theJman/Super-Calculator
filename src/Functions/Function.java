package Functions;

import java.util.ArrayList;

import BasicCalculator.InvalidInputException;

public interface Function {
public String eval(ArrayList<String> args) throws InvalidInputException;
}
