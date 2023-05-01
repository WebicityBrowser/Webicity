package com.github.webicitybrowser.webicitybrowser;

import com.github.webicitybrowser.spec.url.URL;

public interface WebicityArguments {

	URL[] getURLs();
	
	boolean getVerbose();
	
	boolean getPrivate();
	
}
