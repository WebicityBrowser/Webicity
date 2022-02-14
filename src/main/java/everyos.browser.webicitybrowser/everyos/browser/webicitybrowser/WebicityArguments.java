package everyos.browser.webicitybrowser;

import everyos.browser.spec.jnet.URL;

public interface WebicityArguments {
	URL[] getURLs();
	boolean getVerbose();
	boolean getPrivate();
}
