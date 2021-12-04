package everyos.api.getopts;

import everyos.api.getopts.imp.FlagBuilderImp;

/**
 * Represents a type of flag that the argument parser may accept.
 */
public interface Flag {
	
	/**
	 * Used to represent an infinite number of optional arguments for a flag.
	 */
	public static final int INFINITE_ARGUMENTS = -1;
	
	/**
	 * The default ID of a flag.
	 */
	public static final int ID_UNSET = -1;
	
	/**
	 * The ID of the flag automatically created for loose arguments.
	 */
	public static final int NO_FLAG = -2;
	
	/**
	 * Get whether or not this flag is mandatory.
	 * A mandatory flag must be present in the arguments passed to the argument parser.
	 * @return Whether or not this flag is mandatory.
	 */
	boolean isMandatory();
	
	/**
	 * Get whether the same flag may appear twice in the arguments passed to the argument parser.
	 * @return Whether the same flag may appear twice in the arguments passed to the argument parser.
	 */
	boolean getAllowDuplicates();
	
	/**
	 * Get the number of required arguments for this flag.
	 * Required arguments are arguments that must be present in the arguments passed to the argument parser.
	 * @return The number of required arguments for this flag.
	 */
	int getNumberRequiredArguments();
	
	/**
	 * Get the number of optional arguments that this flag accepts.
	 * Optional arguments are arguments that need not to be present in the arguments passed to the argument parser.
	 * Returns INFINITE_ARGUMENTS if the number of arguments this flag accepts is infinite.
	 * @return The number of optional arguments this flag accepts.
	 */
	int getNumberOptionalArguments();
	
	/**
	 * Get the numerical ID of this flag.
	 * @return The numerical ID of this flag.
	 */
	int getID();
	
	/**
	 * Get the name, or long-form, of this flag.
	 * @return The name of this flag.
	 */
	String getName();
	
	/**
	 * Get a list of the alias (or the short-forms) of this flag.
	 * @return The list of alias of this flag.
	 */
	String[] getAlias();
	
	/**
	 * Get the description of this flag, as appears in the help screen.
	 * @return The description of this flag.
	 */
	String getDescription();
	
	/**
	 * Get a builder that can be used to create a flag.
	 * @param name The name of the flag that you will be creating with the builder.
	 * @return The builder that will be used to create a flag.
	 */
	static FlagBuilder createBuilder(String name) {
		return new FlagBuilderImp(name);
	}
	
}
