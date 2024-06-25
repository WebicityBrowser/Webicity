package everyos.api.getopts;

/**
 * An exception thrown if any of the arguments passed to the argument parser are invalid.
 */
public class ParserFailedException extends Exception {
	
	private static final long serialVersionUID = -5966715242190479926L;
	
	public ParserFailedException() {
		super("The parser failed to parse because the input was invalid.");
	}
	
}
