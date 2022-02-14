package everyos.api.getopts.imp;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import everyos.api.getopts.Argument;
import everyos.api.getopts.ArgumentParser;
import everyos.api.getopts.Flag;
import everyos.api.getopts.FlagArgumentPair;
import everyos.api.getopts.FlagArgumentPairCollection;
import everyos.api.getopts.ParserFailedException;

public class ArgumentParserImp implements ArgumentParser {

	private final Flag[] flags;
	private final boolean allowLooseArguments;
	private final String helpHeader;
	private final String helpFooter;
	private final String errorFooter;
	
	private final List<Argument> extraEntries = new ArrayList<>();
	private final List<Flag> flagEntries = new ArrayList<>();
	private final List<List<Argument>> argumentEntries = new ArrayList<>();
	
	private final PrintStream parserLogStream;
	private boolean isParsingFlag;
	//TODO: Allow localization

	//TODO: Reduce number of parameters
	public ArgumentParserImp(Flag[] flags, boolean allowLooseArguments, String helpHeader, String helpFooter, String errorFooter, PrintStream parserLogStream) {
		this.flags = flags;
		this.allowLooseArguments = allowLooseArguments;
		this.helpHeader = helpHeader;
		this.helpFooter = helpFooter;
		this.errorFooter = errorFooter;
		this.parserLogStream = parserLogStream;
	}

	@Override
	public FlagArgumentPairCollection parse(String[] arguments) throws ParserFailedException {
		resetParser();
		
		for (int i = 0; i < arguments.length; i++) {
			processArgument(arguments[i]);
		}
		
		ensureAllArgumentsSuppliedForLastFlag();
		
		return convertParsedDataToFlagArgumentPairCollection();
	}

	@Override
	public void printHelpScreen() {
		printHelpHeader();
		printFlags();
		printHelpFooter();
	}

	private void printHelpHeader() {
		if (helpHeader != null) {
			parserLogStream.println(helpHeader);
			parserLogStream.println();
		}
	}
	
	private void printFlags() {
		for (Flag flag: flags) {
			printFlag(flag);
		}
	}
	
	private void printFlag(Flag flag) {
		if (flag.isMandatory()) {
			parserLogStream.print("(M) ");
		}
		
		parserLogStream.print("--");
		parserLogStream.print(flag.getName());
		for (String alias: flag.getAlias()) {
			parserLogStream.print(", -");
			parserLogStream.print(alias);
		}
		parserLogStream.print(' ');
		//TODO Print Arguments
		parserLogStream.println(flag.getDescription());
	}
	
	private void printHelpFooter() {
		if (helpFooter != null) {
			parserLogStream.println(helpFooter);
			parserLogStream.println();
		}
	}
	
	private void resetParser() {
		extraEntries.clear();
		flagEntries.clear();
		argumentEntries.clear();
		isParsingFlag = false;
	}
	
	private void processArgument(String argument) throws ParserFailedException {
		if (isParsingFlag) {
			stopPargsingCurrentFlagIfOptionalArgumentsMet();
		}
		
		if (isFlagName(argument)) {
			switchToFlagWithName(argument);
		} else if (isFlagAlias(argument)) {
			switchToFlagWithAlias(argument);
		} else if (isParsingFlag) {
			addArgumentToCurrentFlag(argument);
		} else {
			addLooseArgumentIfAllowed(argument);
		}
	}
	
	private void switchToFlagWithName(String name) throws ParserFailedException {
		if (!canSwitchFlag()) {
			error(
				"Attempt to switch to flag of name \"" +name +
				"\" while still supplying arguments for flag \"" + getCurrentFlag().getName() + "\"");
		}
		startNewFlagByName(name.substring(2));
	}
	
	private void switchToFlagWithAlias(String alias) throws ParserFailedException {
		if (!canSwitchFlag()) {
			error(
				"Attempt to switch to flag of alias \"" + alias +
				"\" while still supplying arguments for flag \"" + getCurrentFlag().getName() + "\"");
		}
		startNewFlagByAlias(alias.substring(1));
	}

	private FlagArgumentPairCollection convertParsedDataToFlagArgumentPairCollection() {
		//TODO: Ensure all mandatory flags are met
		
		Map<Integer, List<FlagArgumentPair>> arguments = new HashMap<>();
		
		for (int i = 0; i < flagEntries.size(); i++) {
			FlagArgumentPair flagArgumentPair = createFlagArgumentPairForEntryNumber(i);
			arguments.computeIfAbsent(flagArgumentPair.getFlag().getID(), (id) -> new ArrayList<FlagArgumentPair>())
				.add(flagArgumentPair);
		}
		
		Flag defaultFlag = Flag.createBuilder("").setID(Flag.NO_FLAG).build();
		List<FlagArgumentPair> defaultFlagList = List.of(new FlagArgumentPairImp(defaultFlag, extraEntries.toArray(new Argument[extraEntries.size()])));
		arguments.put(Flag.NO_FLAG, defaultFlagList);
		
		return new FlagArgumentPairCollectionImp(arguments);
	}

	private FlagArgumentPair createFlagArgumentPairForEntryNumber(int i) {
		List<Argument> argList = argumentEntries.get(i);
		Flag flag = flagEntries.get(i);
		
		return new FlagArgumentPairImp(flag, argList.toArray(new Argument[argList.size()]));
	}

	private void stopPargsingCurrentFlagIfOptionalArgumentsMet() {
		Flag flag = getCurrentFlag();
		int maxArguments = flag.getNumberRequiredArguments() + flag.getNumberOptionalArguments();
		if (flag.getNumberOptionalArguments() != Flag.INFINITE_ARGUMENTS &&
			getCurrentArguments().size() > maxArguments) {
			
			isParsingFlag = false;
		}
	}
	
	private void addArgumentToCurrentFlag(String argument) {
		getCurrentArguments().add(new ArgumentImp(argument, message -> error(message)));
	}

	private void addLooseArgumentIfAllowed(String argument) throws ParserFailedException {
		if (!allowLooseArguments) {
			error("Loose argument disabled: \"" + argument +'"');
		}
		extraEntries.add(new ArgumentImp(argument, message -> error(message)));
	}
	
	private boolean canSwitchFlag() {
		return
			!isParsingFlag ||
			getCurrentArguments().size() >= getCurrentFlag().getNumberRequiredArguments();
	}

	private boolean isFlagName(String string) {
		return string.startsWith("--");
	}
	
	private boolean isFlagAlias(String string) {
		return string.startsWith("-") && !isFlagName(string);
	}

	private void ensureAllArgumentsSuppliedForLastFlag() throws ParserFailedException {
		if (!isParsingFlag) {
			return;
		}
		
		Flag flag = getCurrentFlag();
		if (getCurrentArguments().size() < flag.getNumberRequiredArguments()) {
			error("Not enough arguments supplied for flag \"" + flag.getName() +'"');
		}
	}
	
	private void startNewFlag(Flag flag) throws ParserFailedException {
		if (!flag.getAllowDuplicates() && flagEntries.contains(flag)) {
			error("Duplicate flag specified where duplicates not allowed: \"" + flag.getName() +'"');
		}
		
		flagEntries.add(flag);
		argumentEntries.add(new ArrayList<>());
		
		isParsingFlag = true;
	}
	
	private void startNewFlagByName(String name) throws ParserFailedException {
		for (Flag flag: flags) {
			if (flag.getName().equals(name)) {
				startNewFlag(flag);
				return;
			}
		}
		
		error("Not a valid flag name: \"" + name +'"');
	}
	
	private void startNewFlagByAlias(String alias) throws ParserFailedException {
		for (Flag flag: flags) {
			if (checkIfFlagHasAlias(flag, alias)) {
				startNewFlag(flag);
				return;
			}
		}
		
		error("Not a valid flag alias: \"" + alias +'"');
	}
	
	private boolean checkIfFlagHasAlias(Flag flag, String alias) {
		for (String alias2: flag.getAlias()) {
			if (alias2.equals(alias)) {
				return true;
			}
		}
		
		return false;
	}
	
	private void error(String message) throws ParserFailedException {
		parserLogStream.println(message);
		
		if (errorFooter != null) {
			parserLogStream.println(errorFooter);
		}
		
		throw new ParserFailedException();
	}

	private List<Argument> getCurrentArguments() {
		return argumentEntries.get(argumentEntries.size() - 1);
	};
	
	private Flag getCurrentFlag() {
		return flagEntries.get(flagEntries.size() - 1);
	}
	
}
