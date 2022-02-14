package everyos.browser.spec.jfetch.intf;

import everyos.browser.spec.jnet.URL;

public interface Request {
	void setReloadNavigationFlag(boolean b);
	URL getURL();
}
