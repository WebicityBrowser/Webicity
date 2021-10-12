package everyos.api.getopts;

/**
 * Represents a procedure to be executed if the conversion of an argument to an object fails.
 */
public interface ErrorHandler {
	
	/**
	 * Handle the failure of the conversion of an argument to an object.
	 * @param message The reason the conversion failed.
	 * @throws ParserFailedException An exception that may be thrown depending on the error handling style.
	 */
	void error(String message) throws ParserFailedException;
}
