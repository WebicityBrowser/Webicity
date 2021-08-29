package everyos.api.getopts;

public interface ArgumentReader<T> {
	T transform(String input, ErrorHandler errorHandler) throws ParserFailedException;
}
