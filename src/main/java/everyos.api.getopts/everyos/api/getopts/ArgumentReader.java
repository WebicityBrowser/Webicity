package everyos.api.getopts;

public interface ArgumentReader<T> {
	T transform(String input) throws ParserFailedException;
}
