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
	private final boolean allowExtra;
	private final String helpHeader;
	private final String helpFooter;
	private final String errorFooter;
	
	private final List<Argument> extraEntries = new ArrayList<>();
	private final List<Flag> flagEntries = new ArrayList<>();
	private final List<List<Argument>> argumentEntries = new ArrayList<>();
	
	private boolean isParsingFlag;
	//TODO: Allow localization

	public ArgumentParserImp(Flag[] flags, boolean allowExtra, String helpHeader, String helpFooter, String errorFooter) {
		this.flags = flags;
		this.allowExtra = allowExtra;
		this.helpHeader = helpHeader;
		this.helpFooter = helpFooter;
		this.errorFooter = errorFooter;
	}

	@Override
	public FlagArgumentPairCollection parse(String[] arguments, PrintStream dest) throws ParserFailedException {
		extraEntries.clear();
		flagEntries.clear();
		argumentEntries.clear();
		isParsingFlag = false;
		
		for (int i = 0; i < arguments.length; i++) {
			if (isParsingFlag) {
				endIfOptionalArgumentsMet();
			}
			
			if (isFlagName(arguments[i])) {
				if (!canSwitchFlag()) {
					error(
						"Attempt to switch to flag of name \"" + arguments[i] +
						"\" while still supplying arguments for flag \"" + getCurrentFlag().getName() + "\"", dest);
				}
				startNewFlagByName(arguments[i].substring(2), dest);
			} else if (isFlagAlias(arguments[i])) {
				if (!canSwitchFlag()) {
					error(
						"Attempt to switch to flag of alias \"" + arguments[i] +
						"\" while still supplying arguments for flag \"" + getCurrentFlag().getName() + "\"", dest);
				}
				startNewFlagByAlias(arguments[i].substring(1), dest);
			} else if (isParsingFlag) {
				getCurrentArguments().add(new ArgumentImp(arguments[i], message -> error(message, dest)));
			} else {
				if (!allowExtra) {
					error("Loose argument disabled: \"" + arguments[i] +'"', dest);
				}
				extraEntries.add(new ArgumentImp(arguments[i], message -> error(message, dest)));
			}
		}
		
		onFlagEnd(dest);
		
		return gatherFinalFlags();
	}
	
	@Override
	public void printHelpScreen(PrintStream dest) {
		if (helpHeader != null) {
			dest.println(helpHeader);
			dest.println();
		}
		
		for (Flag flag: flags) {
			if (flag.isMandatory()) {
				dest.print("(M) ");
			}
			
			dest.print("--");
			dest.print(flag.getName());
			for (String alias: flag.getAlias()) {
				dest.print(", -");
				dest.print(alias);
			}
			dest.print(' ');
			//TODO Print Arguments
			dest.println(flag.getDescription());
		}
		
		if (errorFooter != null) {
			dest.println();
			dest.println(helpFooter);
		}
	}
	
	private FlagArgumentPairCollection gatherFinalFlags() {
		//TODO: Ensure all mandatory flags are met
		
		Map<Integer, List<FlagArgumentPair>> arguments = new HashMap<>();
		
		for (int i = 0; i < flagEntries.size(); i++) {
			List<Argument> argList = argumentEntries.get(i);
			Flag flag = flagEntries.get(i);
			FlagArgumentPair flagArgumentPair = new FlagArgumentPairImp(flag, argList.toArray(new Argument[argList.size()]));
			arguments.computeIfAbsent(flag.getID(), (id)->new ArrayList<FlagArgumentPair>())
				.add(flagArgumentPair);
		}
		
		Flag defaultFlag = Flag.createBuilder("").setID(Flag.NO_FLAG).build();
		List<FlagArgumentPair> defaultFlagList = List.of(new FlagArgumentPairImp(defaultFlag, extraEntries.toArray(new Argument[extraEntries.size()])));
		arguments.put(Flag.NO_FLAG, defaultFlagList);
		
		return new FlagArgumentPairCollectionImp(arguments);
	}

	private void endIfOptionalArgumentsMet() {
		Flag flag = getCurrentFlag();
		if (flag.getNumberOptionalArguments() != Flag.INFINITE_ARGUMENTS && getCurrentArguments().size() > flag.getNumberRequiredArguments() + flag.getNumberOptionalArguments()) {
			isParsingFlag = false;
		}
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

	private void onFlagEnd(PrintStream dest) throws ParserFailedException {
		if (!isParsingFlag) {
			return;
		}
		
		Flag flag = getCurrentFlag();
		if (getCurrentArguments().size() < flag.getNumberRequiredArguments()) {
			error("Not enough arguments supplied for flag \"" + flag.getName() +'"', dest);
		}
	}
	
	private void startNewFlag(Flag flag, PrintStream dest) throws ParserFailedException {
		if (!flag.getAllowDuplicates() && flagEntries.contains(flag)) {
			error("Duplicate flag specified where duplicates not allowed: \"" + flag.getName() +'"', dest);
		}
		
		flagEntries.add(flag);
		argumentEntries.add(new ArrayList<>());
		
		isParsingFlag = true;
	}
	
	private void startNewFlagByName(String name, PrintStream dest) throws ParserFailedException {
		for (Flag flag: flags) {
			if (flag.getName().equals(name)) {
				startNewFlag(flag, dest);
				return;
			}
		}
		
		error("Not a valid flag name: \"" + name +'"', dest);
	}
	
	private void startNewFlagByAlias(String alias, PrintStream dest) throws ParserFailedException {
		for (Flag flag: flags) {
			for (String alias2: flag.getAlias()) {
				if (alias2.equals(alias)) {
					startNewFlag(flag, dest);
					return;
				}
			}
		}
		
		error("Not a valid flag alias: \"" + alias +'"', dest);
	}
	
	private void error(String message, PrintStream dest) throws ParserFailedException {
		dest.println(message);
		
		if (errorFooter != null) {
			dest.println(errorFooter);
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
