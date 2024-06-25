package com.github.webicitybrowser.spec.http.version.http11;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

import com.github.webicitybrowser.spec.http.HTTPHeaders;
import com.github.webicitybrowser.spec.http.HTTPStatus;

public class HTTP11ResponseHeaderParser {
	
	private HTTP11ResponseHeaderParser() {}

	public static HTTP11ResponseData parse(InputStream inputStream) throws IOException {
		PushbackInputStream pushbackInputStream = new PushbackInputStream(inputStream);
		HTTPStatus status = HTTP11ResponseStatusLineParser.parseStatusLine(pushbackInputStream);
		HTTPHeaders headers = HTTP11ResponseFieldLinesParser.parseFieldLines(pushbackInputStream);
		
		return new HTTP11ResponseData(status, headers);
	}

}
