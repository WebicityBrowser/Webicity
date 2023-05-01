package com.github.webicitybrowser.webicity.protocol;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.webicity.core.net.Connection;
import com.github.webicitybrowser.webicity.core.net.Protocol;

public class AboutProtocol implements Protocol {

	@Override
	public String[] getSchemes() {
		return new String[] { "about" };
	}

	@Override
	public Connection openConnection(URL url) throws IOException {
		return new Connection() {
			@Override
			public URL getURL() {
				return url;
			}
			
			@Override
			public Reader getInputReader() {
				return new StringReader("");
			}
			
			@Override
			public String getContentType() {
				return "text/html";
			}
		};
	}

}
