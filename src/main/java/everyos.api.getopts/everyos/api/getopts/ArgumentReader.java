package everyos.api.getopts;

/**
 * This class represents the procedure used to convert an argument to an object of type T.
 * @param <T> The type of object the procedure converts an argument to.
 */
public interface ArgumentReader<T> {
	
	/**
	 * Convert an argument to an object of type T.
	 * @param input The argument to be converted.
	 * @param errorHandler A procedure to be executed if the conversion fails.
	 * @return The final object created by the conversion.
	 * @throws ParserFailedException An exception that may be thrown if a conversion fails.
	 */
	T transform(String input, ErrorHandler errorHandler) throws ParserFailedException;
	
}
