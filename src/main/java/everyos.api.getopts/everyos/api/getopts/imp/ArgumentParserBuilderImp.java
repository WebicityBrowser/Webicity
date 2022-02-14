package everyos.api.getopts.imp;

import java.io.PrintStream;

import everyos.api.getopts.ArgumentParser;
import everyos.api.getopts.ArgumentParserBuilder;
import everyos.api.getopts.Flag;

public class ArgumentParserBuilderImp implements ArgumentParserBuilder {

	private Flag[] flags;
	private boolean allowLooseArguments;
	private String helpHeader;
	private String helpFooter;
	private String errorFooter;
	private PrintStream parserLogStream;

	@Override
	public ArgumentParserBuilder setFlags(Flag[] flags) {
		this.flags = flags;
		
		return this;
	}

	@Override
	public ArgumentParserBuilder setAllowLooseArguments(boolean allowLooseArguments) {
		this.allowLooseArguments = allowLooseArguments;
		
		return this;
	}

	@Override
	public ArgumentParserBuilder setHelpHeader(String message) {
		this.helpHeader = message;
		
		return this;
	}
	
	@Override
	public ArgumentParserBuilder setHelpFooter(String message) {
		this.helpFooter = message;
		
		return this;
	}

	@Override
	public ArgumentParserBuilder setErrorFooter(String message) {
		this.errorFooter = message;
		
		return this;
	}
	
	@Override
	public ArgumentParserBuilder setLogStream(PrintStream parserLogStream) {
		this.parserLogStream = parserLogStream;
		
		return this;
	}

	@Override
	public ArgumentParser build() {
		return new ArgumentParserImp(flags, allowLooseArguments, helpHeader, helpFooter, errorFooter, parserLogStream);
	}

}
