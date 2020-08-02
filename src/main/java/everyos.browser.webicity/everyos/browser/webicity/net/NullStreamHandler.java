package everyos.browser.webicity.net;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public final class NullStreamHandler {
	private NullStreamHandler() {}
	
	public static URLStreamHandler instance = new URLStreamHandler() {
		@Override protected URLConnection openConnection(URL u) {
			return null;
		}
	};
}
