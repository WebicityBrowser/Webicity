package everyos.browser.webicity.core.ui.renderer;

import everyos.web.spec.uri.URL;

public interface Renderer {

	void start();
	
	void close();
	
	String getName();
	
	URL getURL();
	
}
