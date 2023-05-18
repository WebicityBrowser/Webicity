package com.github.webicitybrowser.spec.http.response;

import com.github.webicitybrowser.spec.url.URL;

public interface HTTPRedirectResponse extends HTTPResponse {

	URL getRedirectURL();
	
}
