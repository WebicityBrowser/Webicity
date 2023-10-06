package com.github.webicitybrowser.spec.http;

import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.webicity.core.net.ProtocolContext;

public record HTTPRequest(URL url, String method, HTTPRedirectHandler redirectHandler) {

	public static HTTPRequest createRequest(URL url, ProtocolContext context) {
		HTTPRedirectHandler redirectHandler = redirectURL -> context.redirectHandler().onRedirectRequest(redirectURL);
		return new HTTPRequest(url, context.action(), redirectHandler);
	}
}
