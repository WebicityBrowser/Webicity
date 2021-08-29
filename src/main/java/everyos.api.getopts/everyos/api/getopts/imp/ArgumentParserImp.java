package everyos.api.getopts.imp;

import java.util.ArrayList;
import java.util.List;

import everyos.api.getopts.Argument;
import everyos.api.getopts.ArgumentParser;
import everyos.api.getopts.Flag;
import everyos.api.getopts.FlagArgumentPair;
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
	
	private boolean isParsingCommand;
	//TODO

	public ArgumentParserImp(Flag[] flags, boolean allowExtra, String helpHeader, String helpFooter, String errorFooter) {
		this.flags = flags;
		this.allowExtra = allowExtra;
		this.helpHeader = helpHeader;
		this.helpFooter = helpFooter;
		this.errorFooter = errorFooter;
	}

	@Override
	public FlagArgumentPair[] parse(String[] arguments) throws ParserFailedException {
		extraEntries.clear();
		flagEntries.clear();
		argumentEntries.clear();
		isParsingCommand = false;
		
		for (int i = 0; i < arguments.length; i++) {
			if (isParsingCommand) {
				endIfOptionalArgumentsMet();
			}
			
			if (isFlagName(arguments[i])) {
				startNewFlagByName(arguments[i].substring(2));
			} else if (isFlagAlias(arguments[i])) {
				startNewFlagByAlias(arguments[i].substring(1));
			} else if (isParsingCommand) {
				getCurrentArguments().add(new ArgumentImp(arguments[i], message -> error(message)));
			} else {
				if (!allowExtra) {
					error("Loose argument disabled: \"" + arguments[i] +'"');
				}
				extraEntries.add(new ArgumentImp(arguments[i], message -> error(message)));
			}
		}
		
		onFlagEnd();
		
		return gatherFinalFlags();
	}
	
	@Override
	public void printHelpScreen() {
		if (helpHeader != null) {
			System.out.println(helpHeader);
			System.out.println();
		}
		
		for (Flag flag: flags) {
			if (flag.isMandatory()) {
				System.out.print("(M) ");
			}
			
			System.out.print("--");
			System.out.print(flag.getName());
			for (String alias: flag.getAlias()) {
				System.out.print(", -");
				System.out.print(alias);
			}
			System.out.print(' ');
			//TODO Print Arguments
			System.out.println(flag.getDescription());
		}
		
		if (errorFooter != null) {
			System.out.println();
			System.out.println(helpFooter);
		}
	}
	
	private FlagArgumentPair[] gatherFinalFlags() {
		//TODO: Ensure all mandatory flags are met
		
		List<FlagArgumentPair> arguments = new ArrayList<>(flagEntries.size()+1);
		
		for (int i = 0; i < flagEntries.size(); i++) {
			List<Argument> argList = argumentEntries.get(i);
			arguments.add(new FlagArgumentPairImp(flagEntries.get(i), argList.toArray(new Argument[argList.size()])));
		}
		
		Flag defaultFlag = Flag.createBuilder("").setID(Flag.NO_FLAG).build();
		arguments.add(new FlagArgumentPairImp(defaultFlag, extraEntries.toArray(new Argument[extraEntries.size()])));
		
		return arguments.toArray(new FlagArgumentPair[arguments.size()]);
	}

	private void endIfOptionalArgumentsMet() {
		Flag flag = getCurrentFlag();
		if (flag.getOptionalArguments() != Flag.INFINITE_ARGUMENTS && getCurrentArguments().size() > flag.getArguments() + flag.getOptionalArguments()) {
			isParsingCommand = false;
		}
	}

	private boolean isFlagName(String string) {
		return string.startsWith("--");
	}
	
	private boolean isFlagAlias(String string) {
		return string.startsWith("-") && !isFlagName(string);
	}

	private void onFlagEnd() throws ParserFailedException {
		if (!isParsingCommand) {
			return;
		}
		
		Flag flag = getCurrentFlag();
		if (getCurrentArguments().size() < flag.getArguments()) {
			error("Not enough arguments supplied for flag \"" + flag.getName() +'"');
		}
	}
	
	private void startNewFlag(Flag flag) throws ParserFailedException {
		if (!flag.allowDuplicates() && flagEntries.contains(flag)) {
			error("Duplicate flag specified where duplicates not allowed: \"" + flag.getName() +'"');
		}
		
		flagEntries.add(flag);
		argumentEntries.add(new ArrayList<>());
		
		isParsingCommand = true;
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
			for (String alias2: flag.getAlias()) {
				if (alias2.equals(alias)) {
					startNewFlag(flag);
					return;
				}
			}
		}
		
		error("Not a valid flag alias: \"" + alias +'"');
	}
	
	private void error(String message) throws ParserFailedException {
		System.out.println(message);
		
		if (errorFooter != null) {
			System.out.println(errorFooter);
		}
		
		throw new ParserFailedException();
	}

	private List<Argument> getCurrentArguments() {
		return argumentEntries.get(argumentEntries.size()-1);
	};
	
	private Flag getCurrentFlag() {
		return flagEntries.get(flagEntries.size()-1);
	}
}
