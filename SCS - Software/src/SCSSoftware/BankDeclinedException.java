package SCSSoftware;

public class BankDeclinedException extends Exception{

	/**
	 * Create an exception without an error message.
	 */
	public BankDeclinedException() {}

	/**
	 * Create an exception with an error message.
	 * 
	 * @param message
	 *            The error message to use.
	 */
	public BankDeclinedException(String message) {
		super(message);
	}
}