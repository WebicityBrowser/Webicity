package everyos.api.getopts;

import everyos.api.getopts.imp.ArgumentParserImp;

public interface ArgumentParser {
	FlagArgumentPair[] parse(String[] arguments);
	
	public static ArgumentParser create(Flag[] flags, boolean allowExtra, String description) {
		return new ArgumentParserImp(flags, allowExtra, description);
	}
}
