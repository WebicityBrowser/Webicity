package everyos.api.getopts;

public interface Argument {
	<T> T read(ArgumentReader<T> reader) throws ParserFailedException;
}
