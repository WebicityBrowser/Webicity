package com.github.webicitybrowser.spec.htmlbrowsers;

import com.github.webicitybrowser.spec.url.URL;

public interface Origin {

	static Origin of(URL url) {
		return new TupleOrigin(url.getScheme(), url.getHost(), url.getPort(), null);
	}
	
}
