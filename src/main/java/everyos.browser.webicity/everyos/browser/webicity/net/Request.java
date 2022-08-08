package everyos.browser.webicity.net;

import java.io.IOException;
import java.net.UnknownHostException;

import everyos.browser.spec.jnet.URL;

public interface Request {
	
	//TODO: Not really sure if this takes Service Workers into account
	URL getURL();
	
	Response send() throws UnknownHostException, IOException;
	
}
