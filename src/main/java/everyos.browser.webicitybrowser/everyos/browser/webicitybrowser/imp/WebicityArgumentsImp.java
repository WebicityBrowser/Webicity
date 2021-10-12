package everyos.browser.webicitybrowser.imp;

import java.util.ArrayList;
import java.util.List;

import everyos.api.getopts.Argument;
import everyos.api.getopts.ArgumentParser;
import everyos.api.getopts.Flag;
import everyos.api.getopts.FlagArgumentPairCollection;
import everyos.api.getopts.ParserFailedException;
import everyos.browser.webicity.net.URL;
import everyos.browser.webicitybrowser.WebicityArguments;

public class WebicityArgumentsImp implements WebicityArguments {
	public static final int HELP_FLAG = 1;
	public static final int PRIVATE_FLAG = 2;
	public static final int VERBOSE_FLAG = 3;
	
	private final boolean isPrivate;
	private final boolean isVerbose;
	private final URL[] urls;
	
	public WebicityArgumentsImp(ArgumentParser parser, FlagArgumentPairCollection arguments) throws ParserFailedException {
		List<URL> urls = new ArrayList<>();
		
		URLArgumentReader urlReader = new URLArgumentReader();
		
		if (arguments.get(HELP_FLAG).length > 0) {
			parser.printHelpScreen(System.out);
			throw new ParserFailedException();
		}
		
		this.isPrivate = arguments.get(PRIVATE_FLAG).length > 0;
		this.isVerbose = arguments.get(VERBOSE_FLAG).length > 0;
		for (Argument argument: arguments.get(Flag.NO_FLAG)[0].getArguments()) {
			urls.add(argument.read(urlReader));
		}
		
		this.urls = urls.toArray(new URL[urls.size()]);
	}

	@Override
	public URL[] getURLs() {
		return urls;
	}

	@Override
	public boolean getVerbose() {
		return isVerbose;
	}

	@Override
	public boolean getPrivate() {
		return isPrivate;
	}
}
