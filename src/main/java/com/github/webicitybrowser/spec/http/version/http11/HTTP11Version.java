package com.github.webicitybrowser.spec.http.version.http11;

import java.io.IOException;
import java.io.InputStream;

import com.github.webicitybrowser.spec.http.HTTPHeaders;
import com.github.webicitybrowser.spec.http.HTTPTransferEncoding;
import com.github.webicitybrowser.spec.http.HTTPVersion;
import com.github.webicitybrowser.spec.http.encoding.contentlength.ContentLengthInputStream;
import com.github.webicitybrowser.spec.http.imp.HTTPContext;
import com.github.webicitybrowser.spec.http.response.HTTPRedirectResponse;
import com.github.webicitybrowser.spec.http.response.HTTPResponse;
import com.github.webicitybrowser.spec.http.response.HTTPSuccessResponse;
import com.github.webicitybrowser.spec.url.InvalidURLException;
import com.github.webicitybrowser.spec.url.URL;

public class HTTP11Version implements HTTPVersion {

	@Override
	public String getName() {
		return HTTP11Constants.HTTP11_NAME;
	}
	
	@Override
	public HTTPResponse get(URL url, HTTPContext context) throws IOException {
		HTTPHeaders headers = createHTTPHeaders(url, context);
		HTTP11GetWriter.writeGetHeader(url, context, headers);
		InputStream inputStream = context.transport().inputStream();
		HTTP11ResponseData httpData = HTTP11ResponseHeaderParser.parse(inputStream);
		if (isRedirect(httpData.status().statusCode())) {
			return createRedirectResponse(httpData);
		}
		
		InputStream responseStream = createResponseStream(inputStream, context, httpData.headers());
		
		return createSuccessResponse(url, httpData, responseStream);
	}

	private boolean isRedirect(int statusCode) {
		return statusCode >= 300 && statusCode < 400;
	}

	private HTTPHeaders createHTTPHeaders(URL url, HTTPContext context) {
		HTTPHeaders headers = HTTPHeaders.create();
		// TODO: Copy request headers
		headers.set("Host", url.getHost());
		headers.set("User-Agent", context.userAgent());
		
		//
		headers.set("Upgrade-Insecure-Requests", "1");
		headers.set("dnt", "1");
		
		headers.set("Sec-Fetch-Dest", "document");
		headers.set("Sec-Fetch-Mode", "navigate");
		headers.set("Sec-Fetch-Site", "cross-site");
		headers.set("Sec-Fetch-User", "?1");
		
		headers.set("Cache-Control", "no-cache");
		//
		
		return headers;
	}

	private HTTPSuccessResponse createSuccessResponse(URL url, HTTP11ResponseData httpData, InputStream inputStream) {
		return new HTTPSuccessResponse() {
			@Override
			public URL getURL() {
				return url;
			}
			
			@Override
			public InputStream getInputStream() {
				return inputStream;
			}
			
			@Override
			public HTTPHeaders getHeaders() {
				return httpData.headers();
			}
		};
	}
	
	private HTTPRedirectResponse createRedirectResponse(HTTP11ResponseData httpData) throws IOException {
		try {
			URL redirectURL = URL.of(httpData.headers().get("Location"));
			return () -> redirectURL;
		} catch (InvalidURLException e) {
			throw new IOException(e);
		}
	}
	
	private InputStream createResponseStream(InputStream inputStream, HTTPContext context, HTTPHeaders httpHeaders) throws IOException {
		String encodingName = httpHeaders.get("Transfer-Encoding");
		if (encodingName == null) {
			return createDefaultEncodingResponseStream(inputStream, context, httpHeaders);
		}
		
		HTTPTransferEncoding encoding = context.transferEncodings().get(encodingName);
		if (encoding == null) {
			throw new IOException("Unrecognized transfer encoding: " + encodingName + "!");
		}
		
		return encoding.decode(inputStream);
	}

	private InputStream createDefaultEncodingResponseStream(InputStream inputStream, HTTPContext context, HTTPHeaders httpHeaders) {
		if (!httpHeaders.has("Content-Length")) {
			return inputStream;
		}
		
		int contentLength = Integer.valueOf(httpHeaders.get("Content-Length"));
		return new ContentLengthInputStream(inputStream, contentLength);
	}

}
