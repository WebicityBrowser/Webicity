package com.github.webicitybrowser.spec.http.version.http11;

import java.io.IOException;
import java.io.PushbackInputStream;

import com.github.webicitybrowser.spec.http.HTTPStatus;
import com.github.webicitybrowser.spec.http.util.ParseUtil;
import com.github.webicitybrowser.spec.infra.util.ASCIIUtil;

public final class HTTP11ResponseStatusLineParser {
	
	private HTTP11ResponseStatusLineParser() {}

	public static HTTPStatus parseStatusLine(PushbackInputStream inputStream) throws IOException {
		String httpVersion = parseHTTPVersion(inputStream);
		checkHTTPVersion(httpVersion);
		ParseUtil.eatSpace(inputStream);
		int statusCode = parseStatusCode(inputStream);
		ParseUtil.eatSpace(inputStream);
		String statusMessage = parseStatusMessage(inputStream);
		ParseUtil.eatCRLF(inputStream);
		
		return new HTTPStatus(statusCode, statusMessage);
	}

	private static String parseHTTPVersion(PushbackInputStream inputStream) throws IOException {
		ParseUtil.eatString(inputStream, "HTTP");
		
		int ch = inputStream.read();
		ParseUtil.expect("char '/'", '/', ch);
		
		int majorVersion = ParseUtil.eatDigit(inputStream);
		
		ch = inputStream.read();
		ParseUtil.expect("char '.'", '.', ch);
		
		int minorVersion = ParseUtil.eatDigit(inputStream);
		
		return "HTTP/" + majorVersion + "." + minorVersion;
	}

	private static int parseStatusCode(PushbackInputStream inputStream) throws IOException {
		int statusCode = 0;
		for (int i = 0; i < 3; i++) {
			statusCode *= 10;
			int ch = inputStream.read();
			if (!ASCIIUtil.isASCIIDigit(ch)) {
				ParseUtil.expect("a digit", ch);
			}
			statusCode += ASCIIUtil.fromASCIINumericalCharacter(ch);
		}
		
		return statusCode;
	}
	
	private static String parseStatusMessage(PushbackInputStream inputStream) throws IOException {
		StringBuilder statusMessage = new StringBuilder();
		while (true) {
			int ch = inputStream.read();
			if (!(ASCIIUtil.isVisibleASCIICharacter(ch) || ParseUtil.isObsText(ch) || ch == 0x09 || ch == ' ' || ch == '\r')) {
				ParseUtil.expect("a visible ASCII character or CRLF", ch);
			}
			if (ch == '\r') {
				inputStream.unread(ch);
				return statusMessage.toString();
			}
			statusMessage.appendCodePoint(ch);
		}
	}
	
	private static void checkHTTPVersion(String version) {
		if (!version.equals("HTTP/1.1")) {
			throw new UnsupportedOperationException("HTTP: Downgrading HTTP protocol not supported at this time!");
		}
	}
	
}
