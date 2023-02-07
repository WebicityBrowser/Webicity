package everyos.web.spec.uri;

import java.net.MalformedURLException;

import everyos.web.spec.uri.parser.URLParser;

public interface URL extends URI {

	public static URL createFromString(String url) throws MalformedURLException {
		return new URL() {

			@Override
			public String getScheme() {
				return null;
			}

			@Override
			public String getUserInfo() {
				return null;
			}

			@Override
			public String getHost() {
				return null;
			}

			@Override
			public String getAuthority() {
				return null;
			}

			@Override
			public String getPath() {
				return null;
			}

			@Override
			public String getQuery() {
				return null;
			}

			@Override
			public String getFragment() {
				return null;
			}

			@Override
			public int getPort() {
				return 0;
			}
			
			@Override
			public String toString() {
				return url;
			}
			
		};
		//return new URLParser().parse(null, url, null, null);
	}

	public static URL createFromStringSafe(String string) {
		try {
			return createFromString(string);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
