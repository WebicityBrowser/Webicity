package everyos.browser.webicity.net;

import java.io.IOException;
import java.net.UnknownHostException;

public interface Request {
	//TODO: Not really sure if this takes Service Workers into account
	public URL getURL();
	public Response send() throws UnknownHostException, IOException;
}
