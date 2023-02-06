package everyos.web.spec.uri;

import java.net.MalformedURLException;

import everyos.web.spec.uri.imp.URLImp;

public interface URL extends URI {

	public static URL createFromString(String url) throws MalformedURLException {
		return new URLImp();
	}

	public static URL createFromStringSafe(String string) {
		try {
			return createFromString(string);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
