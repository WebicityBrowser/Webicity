package everyos.web.spec.uri;

import java.net.MalformedURLException;

public interface URL extends URI {

	public static URL createFromString(String url) throws MalformedURLException {
		return new URL() {};
	}
	
}
