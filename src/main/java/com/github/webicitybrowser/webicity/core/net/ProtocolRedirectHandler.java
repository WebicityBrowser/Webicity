package com.github.webicitybrowser.webicity.core.net;

import com.github.webicitybrowser.spec.url.URL;

public interface ProtocolRedirectHandler {

	boolean onRedirectRequest(URL redirectURL);
	
}
