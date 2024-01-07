package com.github.webicitybrowser.webicitybrowser.engine.protocol;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.webicity.core.net.Connection;
import com.github.webicitybrowser.webicity.core.net.Protocol;
import com.github.webicitybrowser.webicity.core.net.ProtocolContext;

public class WebicityProtocol implements Protocol {

	@Override
	public String[] getSchemes() {
		return new String[] { "webicity" };
	}

	@Override
	public Connection openConnection(URL url, ProtocolContext context) throws IOException {

		StringBuilder fullPath = new StringBuilder();
		if(!isFileExtensionPresent(url.getPath())) {
			fullPath.append("pages/" + url.getHost() + ( url.getPath().equals("/") ? "" : url.getPath() ) + ".html");
		} else {
			fullPath.append("." + url.getPath());
		}
		InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(
			fullPath.toString()
		);
		BufferedInputStream bufferedStream = new BufferedInputStream(inputStream);

		return new Connection() {
			@Override
			public InputStream getInputStream() {
				return bufferedStream;
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

	private boolean isFileExtensionPresent(String path) {
		for (int i = path.length() - 1; i >= 0 ; i--) {
			if(path.charAt(i) == '/') {
				break;
			}
			if(path.charAt(i) == '.') {
				return true;
			}
		}
		return false;
	}

}
