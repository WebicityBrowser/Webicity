package everyos.api.getopts;

public interface ErrorHandler {
	void error(String message) throws ParserFailedException;
}
