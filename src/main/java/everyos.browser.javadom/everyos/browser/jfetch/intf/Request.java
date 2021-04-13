package everyos.browser.jfetch.intf;

import everyos.browser.webicity.net.URL;

public interface Request {
	void setReloadNavigationFlag(boolean b);
	URL getURL();
}
