package everyos.api.getopts.imp;

import java.util.ArrayList;
import java.util.List;

import everyos.api.getopts.Argument;
import everyos.api.getopts.ArgumentParser;
import everyos.api.getopts.Flag;
import everyos.api.getopts.FlagArgumentPair;

public class ArgumentParserImp implements ArgumentParser {

	private final Flag[] flags;
	private final boolean allowExtra;
	private String description;
	
	private final List<Argument> extras = new ArrayList<>();
	//TODO

	public ArgumentParserImp(Flag[] flags, boolean allowExtra, String description) {
		this.flags = flags;
		this.allowExtra = allowExtra;
		this.description = description;
	}

	@Override
	public FlagArgumentPair[] parse(String[] arguments) {
		boolean isParsingCommand = false;
		
		for (int i = 0; i < arguments.length; i++) {
			if (isParsingCommand) {
				
			} else {
				
			}
		}
		
		return new FlagArgumentPair[0];
	}
}
