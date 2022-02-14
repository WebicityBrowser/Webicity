package everyos.api.getopts;

import everyos.api.getopts.imp.ArgumentParserBuilderImp;

/**
 * Represents an argument parser to parse given arguments.
 */
public interface ArgumentParser {
	
	/**
	 * Parse the supplied arguments.
	 * @param arguments The arguments to be parsed.
	 * @return The result of parsing the arguments.
	 * @throws ParserFailedException Thrown if any of the arguments given are invalid.
	 */
	FlagArgumentPairCollection parse(String[] arguments) throws ParserFailedException;
	
	/**
	 * Prints the help screen to this parser's output stream.
	 */
	void printHelpScreen();
	
	/**
	 * Returns a builder used to create a parser.
	 * @return The builder used to create a parser.
	 */
	public static ArgumentParserBuilder createBuilder() {
		return new ArgumentParserBuilderImp();
	}
	
}
