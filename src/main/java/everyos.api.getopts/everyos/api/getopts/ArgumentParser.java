package everyos.api.getopts;

import everyos.api.getopts.imp.ArgumentParserBuilderImp;

public interface ArgumentParser {
	FlagArgumentPair[] parse(String[] arguments) throws ParserFailedException;
	void printHelpScreen();
	
	public static ArgumentParserBuilder createBuilder() {
		return new ArgumentParserBuilderImp();
	}
}
