package everyos.browser.webicity.net.protocol;

import everyos.browser.spec.jnet.URL;
import everyos.browser.webicity.net.Request;

public interface Protocol {
	public Request getGenericRequest(URL url);
}
