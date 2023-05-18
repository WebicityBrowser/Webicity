package com.github.webicitybrowser.spec.http;

import com.github.webicitybrowser.spec.url.URL;

public interface HTTPRedirectHandler {

	boolean onRedirectRequest(URL redirectURL);
	
}
