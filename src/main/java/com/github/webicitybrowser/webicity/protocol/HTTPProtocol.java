package com.github.webicitybrowser.webicity.protocol;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import com.github.webicitybrowser.spec.http.HTTPResponse;
import com.github.webicitybrowser.spec.http.HTTPService;
import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.webicity.core.net.Connection;
import com.github.webicitybrowser.webicity.core.net.Protocol;

public class HTTPProtocol implements Protocol {

	private final HTTPService httpService;

	public HTTPProtocol(HTTPService httpService) {
		this.httpService = httpService;
	}

	@Override
	public String[] getSchemes() {
		return new String[] { "http", "https" };
	}

	@Override
	public Connection openConnection(URL url) throws IOException {
		HTTPResponse response = httpService.get(url);
		
		return createConnection(response, url);
	}

	private Connection createConnection(HTTPResponse response, URL url) {
		return new Connection() {
			@Override
			public URL getURL() {
				return url;
			}
			
			@Override
			public Reader getInputReader() {
				return new InputStreamReader(response.getInputStream(), StandardCharsets.UTF_8);
			}
			
			@Override
			public String getContentType() {
				return parseContentType(response.getHeaders().get("Content-Type"));
			}
		};
	}
	
	private String parseContentType(String field) {
		int semicolonIndex = field.indexOf(';');
		if (semicolonIndex != -1) {
			return field.substring(0, semicolonIndex);
		}
		
		return field;
	}
	
}
