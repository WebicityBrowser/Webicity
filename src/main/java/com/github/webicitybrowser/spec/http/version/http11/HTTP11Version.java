package com.github.webicitybrowser.spec.http.version.http11;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.github.webicitybrowser.spec.http.HTTPHeaders;
import com.github.webicitybrowser.spec.http.HTTPResponse;
import com.github.webicitybrowser.spec.http.HTTPTransferEncoding;
import com.github.webicitybrowser.spec.http.HTTPVersion;
import com.github.webicitybrowser.spec.http.imp.HTTPContext;
import com.github.webicitybrowser.spec.url.URL;

public class HTTP11Version implements HTTPVersion {

	@Override
	public String getName() {
		return "HTTP/1.1";
	}
	
	@Override
	public HTTPResponse get(URL url, HTTPContext context) throws IOException {
		writeGetHeader(url, context);
		InputStream inputStream = context.transport().inputStream();
		HTTP11ResponseData httpData = HTTP11ResponseHeaderParser.parse(inputStream);
		InputStream responseStream = createResponseStream(inputStream, context, httpData.headers());
		
		return createResponse(httpData, responseStream);
	}

	private void writeGetHeader(URL url, HTTPContext context) throws IOException {
		OutputStream outputStream = context.transport().outputStream();
		
		StringBuilder headerBuilder = new StringBuilder();
		writeHTTPRequestLine(headerBuilder, url, "GET");
		headerBuilder.append("\r\n\r\n");

		outputStream.write(headerBuilder.toString().getBytes());
	}

	private void writeHTTPRequestLine(StringBuilder headerBuilder, URL url, String method) {
		headerBuilder.append(method);
		headerBuilder.append(" ");
		headerBuilder.append(url.getPath());
		headerBuilder.append(" ");
		headerBuilder.append(getName());
	}
	
	private HTTPResponse createResponse(HTTP11ResponseData httpData, InputStream inputStream) {
		return new HTTPResponse() {
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
	
	private InputStream createResponseStream(InputStream inputStream, HTTPContext context, HTTPHeaders httpHeaders) throws IOException {
		String encodingName = httpHeaders.get("Transfer-Encoding");
		if (encodingName == null) {
			return inputStream;
		}
		
		HTTPTransferEncoding encoding = context.transferEncodings().get(encodingName);
		if (encoding == null) {
			throw new IOException("Unrecognized transfer encoding: " + encodingName + "!");
		}
		
		return encoding.decode(inputStream);
	}

}
