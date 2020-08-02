package everyos.browser.webicity.net.request;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;

import everyos.browser.webicity.net.response.Response;

public interface Request {
	//TODO: Not really sure if this takes Service Workers into account
	public URL getURL();
	public Response send() throws UnknownHostException, IOException;
}
