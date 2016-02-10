package Functions;

import BasicCalculator.InvalidInputException;

public class InvalidFunctionUseException extends InvalidInputException {

	private static final long serialVersionUID = 1L;

	public InvalidFunctionUseException() {
		super();
		message = "Incorrect amount of arguments for function.";
	}

	public InvalidFunctionUseException(String functionName) {
		message = "Incorrect amount of arguments for function: " + functionName;
	}
	

}
