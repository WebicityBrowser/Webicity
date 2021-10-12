package everyos.api.getopts;

import java.io.PrintStream;

import everyos.api.getopts.imp.ArgumentParserBuilderImp;

/**
 * Represents an argument parser to parse given arguments.
 */
public interface ArgumentParser {
	/**
	 * Parse the supplied arguments.
	 * @param arguments The arguments to be parsed.
	 * @param dest The stream that any messages shall be printed to.
	 * @return The result of parsing the arguments.
	 * @throws ParserFailedException Thrown if any of the arguments given are invalid.
	 */
	FlagArgumentPairCollection parse(String[] arguments, PrintStream dest) throws ParserFailedException;
	
	/**
	 * Prints the help screen to the given stream.
	 * @param dest The stream that the help screen shall be printed to.
	 */
	void printHelpScreen(PrintStream dest);
	
	/**
	 * Returns a builder used to create a parser.
	 * @return The builder used to create a parser.
	 */
	public static ArgumentParserBuilder createBuilder() {
		return new ArgumentParserBuilderImp();
	}
}
