
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
	public String getPath() {
		return url.getPath();
	}

}
