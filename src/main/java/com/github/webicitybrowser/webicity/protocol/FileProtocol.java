package com.github.webicitybrowser.webicity.protocol;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.webicity.core.net.Connection;
import com.github.webicitybrowser.webicity.core.net.Protocol;

public class FileProtocol implements Protocol {

	@Override
	public String[] getSchemes() {
		return new String[] { "file" };
	}

	@Override
	public Connection openConnection(URL url) throws IOException {
		InputStream inputStream = new FileInputStream(new File(url.getPath()));
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
