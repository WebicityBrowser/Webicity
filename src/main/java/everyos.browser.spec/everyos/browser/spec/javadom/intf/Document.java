package everyos.browser.spec.javadom.intf;

import java.util.List;

import everyos.browser.spec.jcss.intf.CSSStyleSheet;
import everyos.browser.spec.jhtml.browsing.BrowsingContext;
import everyos.browser.spec.jhtml.browsing.Origin;
import everyos.browser.spec.jhtml.intf.Window;
import everyos.browser.webicity.net.URL;

public interface Document extends Node {
	public static final String QUIRKS_MODE = "quirks";
	public static final String HTML = "html";
	
	void setTitle(String text);
	String getTitle();
	
	//TODO: Unofficial methods, move elsewhere
	// These also create an unwanted dependency on jhtml and jcss
	Origin getOrigin();
	URL getURLAsURL();
	BrowsingContext getTopLevelContext();
	List<CSSStyleSheet> getDocumentOrShadowRootCSSStyleSheets();
	
	//TODO: This method definitely should not exist
	Window getRelevantGlobalObject();
}
