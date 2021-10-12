package everyos.api.getopts;

/**
 * Indicates which arguments where parsed for a flag.
 */
public interface FlagArgumentPair {
	/**
	 * Returns the flag that the arguments in this pair represent.
	 * @return The flag that the arguments in this pair represent.
	 */
	Flag getFlag();
	
	/**
	 * Returns the arguments that were parsed for the flag given.
	 * @return The arguments that were parsed for the flag given.
	 */
	Argument[] getArguments();
}
