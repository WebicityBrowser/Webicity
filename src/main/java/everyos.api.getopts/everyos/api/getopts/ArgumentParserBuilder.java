package everyos.api.getopts;

public interface ArgumentParserBuilder {

	ArgumentParserBuilder setFlags(Flag[] flags);
	ArgumentParserBuilder setAllowLooseArguments(boolean allowLooseArguments);
	ArgumentParserBuilder setHelpHeader(String message);
	ArgumentParserBuilder setHelpFooter(String message);
	ArgumentParserBuilder setErrorFooter(String message);
	
	ArgumentParser build();

}
