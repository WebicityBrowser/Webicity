
package com.github.webicitybrowser.spec.url.imp;

import com.github.webicitybrowser.spec.url.URL;

public class URLImp implements URL {
	
	private final java.net.URL url;

	public URLImp(java.net.URL url) {
		this.url = url;
	}

	@Override
	public String getScheme() {
		return url.getProtocol();
	}
	
	@Override
	public String getHost() {
		return url.getHost();
	}

	@Override
	public int getPort() {
		return url.getPort();
	}

	@Override
	public String getPath() {
		return url.getPath().isEmpty() ? "/" : url.getPath();
	}
	
	@Override
	public String getQuery() {
		return url.getQuery();
	}
	
	@Override
	public String toString() {
		return url.toString();
	}

}
