package everyos.browser.webicitybrowser;

import everyos.browser.webicity.net.URL;

public interface WebicityArguments {
	URL[] getURLs();
	boolean getVerbose();
	boolean getPrivate();
}
