package com.github.webicitybrowser.webicity.protocol;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.webicity.core.net.Connection;
import com.github.webicitybrowser.webicity.core.net.Protocol;
import com.github.webicitybrowser.webicity.core.net.ProtocolContext;

public class AboutProtocol implements Protocol {

	@Override
	public String[] getSchemes() {
		return new String[] { "about" };
	}

	@Override
	public Connection openConnection(URL url, ProtocolContext context) throws IOException {
		return new Connection() {
			@Override
			public URL getURL() {
				return url;
			}
			
			@Override
			public InputStream getInputStream() {
				return new ByteArrayInputStream(new byte[0]);
			}
			
			@Override
			public String getContentType() {
				return "text/html";
			}
		};
	}

}
