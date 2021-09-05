package everyos.browser.spec.jfetch.intf;

import everyos.browser.webicity.net.URL;

public interface Request {
	void setReloadNavigationFlag(boolean b);
	URL getURL();
}
