package everyos.api.getopts;

import java.io.PrintStream;

/**
 * A builder to create an argument parser.
 */
public interface ArgumentParserBuilder {
	
	/**
	 * Set the flags that are supported by the argument parser.
	 * @param flags The flags to be supported by the argument parser.
	 * @return The current builder.
	 */
	ArgumentParserBuilder setFlags(Flag[] flags);
	
	/**
	 * Sets whether arguments not associated with a flag are allowed to appear.
	 * @param allowLooseArguments Whether arguments not associated with a flag are allowed to appear.
	 * @return The current builder.
	 */
	ArgumentParserBuilder setAllowLooseArguments(boolean allowLooseArguments);
	
	/**
	 * Set the message that appears before the help screen.
	 * @param message The message that appears before the help screen.
	 * @return The current builder.
	 */
	ArgumentParserBuilder setHelpHeader(String message);
	
	/**
	 * Set the message that appears after the help screen.
	 * @param message The message that appears after the help screen.
	 * @return The current builder.
	 */
	ArgumentParserBuilder setHelpFooter(String message);
	
	/**
	 * Set the message that appears if an argument is incorrect.
	 * @param message The message that appears if an argument is incorrect.
	 * @return The current builder.
	 */
	ArgumentParserBuilder setErrorFooter(String message);
	
	/**
	 * Set the stream that this parser will log output to.
	 * @param parserLogStream The stream that this parser will log output to.
	 * @return The current builder.
	 */
	ArgumentParserBuilder setLogStream(PrintStream parserLogStream);
	
	/**
	 * Create the final argument parser from the given settings.
	 * @return The final argument parser.
	 */
	ArgumentParser build();

}
