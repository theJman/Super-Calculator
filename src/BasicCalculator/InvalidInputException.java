package BasicCalculator;

public class InvalidInputException extends Exception {

	private static final long serialVersionUID = 1L;

	private String message;
	/**
	 * Constructs a new InvalidInputException with a standerd message
	 */
	public InvalidInputException(){
		super("Invalid Inpupt");
		message = "Invalid Inpupt";	
	}
	/**
	 * Constructs a new InvalidInputException with a custom message
	 * @param message
	 */
	public InvalidInputException(String message){
		super(message);
		this.message = message;
	}
	/**
	 * gets the message
	 */
	@Override
	public String getMessage(){
		return message;
	}
	
}
