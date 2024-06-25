package com.github.webicitybrowser.webicity.protocol;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.webicity.core.net.Connection;
import com.github.webicitybrowser.webicity.core.net.Protocol;
import com.github.webicitybrowser.webicity.core.net.ProtocolContext;

public class FileProtocol implements Protocol {

	@Override
	public String[] getSchemes() {
		return new String[] { "file" };
	}

	@Override
	public Connection openConnection(URL url, ProtocolContext context) throws IOException {
		InputStream inputStream = new FileInputStream(new File(url.getPath()));
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

}
