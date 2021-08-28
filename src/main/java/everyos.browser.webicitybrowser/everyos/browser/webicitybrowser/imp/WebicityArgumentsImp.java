package everyos.browser.webicitybrowser.imp;

import java.util.ArrayList;
import java.util.List;

import everyos.api.getopts.Argument;
import everyos.api.getopts.Flag;
import everyos.api.getopts.FlagArgumentPair;
import everyos.api.getopts.ParserFailedException;
import everyos.browser.webicity.net.URL;
import everyos.browser.webicitybrowser.WebicityArguments;

public class WebicityArgumentsImp implements WebicityArguments {
	public static final int PRIVATE_FLAG = 1;
	public static final int VERBOSE_FLAG = 2;
	
	private final boolean isPrivate;
	private final boolean isVerbose;
	private URL[] urls;
	
	public WebicityArgumentsImp(FlagArgumentPair[] flags) throws ParserFailedException {
		boolean isPrivate = false;
		boolean isVerbose = false;
		
		List<URL> urls = new ArrayList<>();
		
		URLArgumentReader urlReader = new URLArgumentReader();
		
		for (FlagArgumentPair pair: flags) {
			if (pair.getFlag().getID() == PRIVATE_FLAG) {
				isPrivate = true;
			} else if (pair.getFlag().getID() == VERBOSE_FLAG) {
				isVerbose = true;
			} else if (pair.getFlag().getID() == Flag.NO_FLAG) {
				for (Argument argument: pair.getArguments()) {
					urls.add(argument.read(urlReader));
				}
			}
		}
		
		this.isPrivate = isPrivate;
		this.isVerbose = isVerbose;
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
