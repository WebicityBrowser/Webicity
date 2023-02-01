package everyos.browser.webicitybrowser;

import everyos.web.spec.uri.URL;

public interface WebicityArguments {

	URL[] getURLs();
	
	boolean getVerbose();
	
	boolean getPrivate();
	
}
