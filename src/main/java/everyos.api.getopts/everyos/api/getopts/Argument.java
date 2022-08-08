package everyos.api.getopts;

/**
 * Represents an argument detected by the argument parser.
 */
public interface Argument {
	
	/**
	 * Convert the argument to an object of type T.
	 * @param <T> The type of object that the argument should be converted to.
	 * @param reader The class that will convert the argument to the object of type T.
	 * @return The object of type T created from the arguments.
	 * @throws ParserFailedException Thrown if the argument cannot be converted to an object of type T.
	 */
	<T> T read(ArgumentReader<T> reader) throws ParserFailedException;
	
}
