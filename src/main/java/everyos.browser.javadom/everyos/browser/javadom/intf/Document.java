package everyos.browser.javadom.intf;

import everyos.browser.jhtml.browsing.BrowsingContext;
import everyos.browser.jhtml.browsing.Origin;
import everyos.browser.jhtml.intf.Window;
import everyos.browser.webicity.net.URL;

public interface Document extends Node {
	public static final String QUIRKS_MODE = "quirks";
	public static final String HTML = "html";
	
	void setTitle(String text);
	String getTitle();
	
	//TODO: Unofficial methods, move elsewhere
	// These also create an unwanted dependency on jhtml
	Origin getOrigin();
	URL getURLAsURL();
	BrowsingContext getTopLevelContext();
	
	//TODO: This method definitely should not exist
	Window getRelevantGlobalObject();
}
