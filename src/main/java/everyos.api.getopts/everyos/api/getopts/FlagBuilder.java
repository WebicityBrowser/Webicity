package everyos.api.getopts;

/**
 * A builder used to build a type of flag used by the argument parser.
 */
public interface FlagBuilder {
	/**
	 * Sets whether or not this flag is mandatory.
	 * A mandatory flag must be present in the arguments passed to the argument parser.
	 * @param mandatory Whether or not this flag is mandatory.
	 * @return The current builder.
	 */
	FlagBuilder setMandatory(boolean mandatory);
	
	/**
	 * Set whether the same flag may appear twice in the arguments passed to the argument parser.
	 * @param allowDuplicates Whether the same flag may appear twice in the arguments passed to the argument parser.
	 * @return The current builder.
	 */
	FlagBuilder setAllowDuplicates(boolean allowDuplicates);
	
	/**
	 * Sets the number of required arguments for this flag.
	 * Required arguments are arguments that must be present in the arguments passed to the argument parser.
	 * @param numberArguments The number of required arguments for this flag.
	 * @return The current builder.
	 */
	FlagBuilder setNumberRequiredArguments(int numberArguments);
	
	/**
	 * Sets the number of optional arguments for this flag.
	 * Optional arguments are arguments that need not to be present in the arguments passed to the argument parser.
	 * Use INFINITE_ARGUMENTS if the number of arguments this flag accepts is infinite.
	 * @param numberOptionalArguments The number of optional arguments for this flag.
	 * @return The current builder.
	 */
	FlagBuilder setNumberOptionalArguments(int numberOptionalArguments);
	
	/**
	 * Sets the ID of the flag being built.
	 * @param id The ID of the flag being built.
	 * @return The current builder.
	 */
	FlagBuilder setID(int id);
	
	/**
	 * Sets the list of alias that can be used to represent this flag.
	 * @param alias The list of alias that can be used to represent this flag.
	 * @return The current builder.
	 */
	FlagBuilder setAlias(String... alias);
	
	/**
	 * Sets the description of the flag displayed in the help screen.
	 * @param description The description of the flag displayed in the help screen.
	 * @return The current builder.
	 */
	FlagBuilder setDescription(String description);
	
	/**
	 * Create a flag from the settings in this builder.
	 * @return The flag created from the settings in this builder.
	 */
	Flag build();
}
