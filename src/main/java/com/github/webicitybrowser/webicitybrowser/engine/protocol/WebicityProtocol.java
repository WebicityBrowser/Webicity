package com.github.webicitybrowser.webicitybrowser.engine.protocol;

import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.webicity.core.net.Connection;
import com.github.webicitybrowser.webicity.core.net.Protocol;
import com.github.webicitybrowser.webicity.core.net.ProtocolContext;

import java.io.*;

public class WebicityProtocol implements Protocol {
	@Override
	public String[] getSchemes() {
		return new String[]{"webicity"};
	}

	@Override
	public Connection openConnection(URL url, ProtocolContext context) throws IOException {

		StringBuilder path = new StringBuilder(
			url.toString().substring( url.getHost().length() + 3)
		);

		if(!path.toString().contains(".html")) {
			path.append(".html");
		}

		InputStream inputStream = new FileInputStream("./src/main/resources/pages" + path);
		BufferedInputStream bufferedStream = new BufferedInputStream(inputStream);
		Reader reader = new InputStreamReader(bufferedStream);

		return new Connection() {
			@Override
			public Reader getInputReader() {
				return reader;
			}

			@Override
			public String getContentType() {
				return "text/html";
			}

			@Override
			public URL getURL() {
				return url;
			}
		};
	}

}
