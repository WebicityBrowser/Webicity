package com.github.webicitybrowser.spec.http.version.http11;

import java.io.IOException;
import java.io.PushbackInputStream;

import com.github.webicitybrowser.spec.http.HTTPHeaders;
import com.github.webicitybrowser.spec.http.HTTPHeaders.HeaderEntry;
import com.github.webicitybrowser.spec.http.util.ParseUtil;
import com.github.webicitybrowser.spec.infra.util.ASCIIUtil;

public final class HTTP11ResponseFieldLinesParser {

	private HTTP11ResponseFieldLinesParser() {}

	public static HTTPHeaders parseFieldLines(PushbackInputStream inputStream) throws IOException {
		HTTPHeaders headers = HTTPHeaders.create();
		while (true) {
			int ch = inputStream.read();
			inputStream.unread(ch);
			if (ch == '\r') {
				break;
			}
			parseFieldLine(inputStream, headers);
		}
		ParseUtil.eatCRLF(inputStream);
		for (HeaderEntry entry: headers) {
			System.out.println(entry.headerName() + ": " + entry.headerValue());
		}
		
		return headers;
	}

	private static void parseFieldLine(PushbackInputStream inputStream, HTTPHeaders headers) throws IOException {
		String fieldName = ParseUtil.eatToken(inputStream);
		
		int ch = inputStream.read();
		ParseUtil.expect("char ':'", ':', ch);
		
		ParseUtil.eatOptionalWhitespace(inputStream);
		
		String fieldValue = parseFieldValue(inputStream);
		
		ParseUtil.eatOptionalWhitespace(inputStream);
		ParseUtil.eatCRLF(inputStream);
		
		addFieldToHeader(headers, fieldName, fieldValue);
	}

	private static String parseFieldValue(PushbackInputStream inputStream) throws IOException {
		StringBuilder fieldValueBuilder = new StringBuilder();
		int ch;
		// TODO: Be more strict in parsing rules
		while (isFieldVisibleChar(ch = inputStream.read()) || ch == ' ' || ch == 0x09) {
			fieldValueBuilder.appendCodePoint(ch);
		}
		inputStream.unread(ch);
		
		return fieldValueBuilder.toString();
	}
	
	private static boolean isFieldVisibleChar(int ch) {
		return ASCIIUtil.isVisibleASCIICharacter(ch) || ParseUtil.isObsText(ch);
	}
	
	private static void addFieldToHeader(HTTPHeaders headers, String fieldName, String fieldValue) {
		if (headers.has(fieldName)) {
			// TODO: Set-Cookie will not be handled correctly
			String oldValue = headers.get(fieldName);
			headers.set(fieldName, oldValue + ", " + fieldValue);
		} else {
			headers.set(fieldName, fieldValue);
		}
	}
	
}
