package everyos.api.getopts.imp;

import everyos.api.getopts.Argument;
import everyos.api.getopts.ArgumentReader;
import everyos.api.getopts.ErrorHandler;
import everyos.api.getopts.ParserFailedException;

public class ArgumentImp implements Argument {

	private final String argument;
	private final ErrorHandler errorHandler;

	public ArgumentImp(String argument, ErrorHandler errorHandler) {
		this.argument = argument;
		this.errorHandler = errorHandler;
	}

	@Override
	public <T> T read(ArgumentReader<T> reader) throws ParserFailedException {
		return reader.transform(argument, errorHandler);
	}
	
}
