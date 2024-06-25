package com.github.webicitybrowser.spec.http.version.http11;

import java.io.IOException;
import java.io.OutputStream;

import com.github.webicitybrowser.spec.http.HTTPHeaders;
import com.github.webicitybrowser.spec.http.HTTPHeaders.HeaderEntry;
import com.github.webicitybrowser.spec.http.imp.HTTPContext;
import com.github.webicitybrowser.spec.url.URL;

public final class HTTP11GetWriter {

	private HTTP11GetWriter() {}
	
	public static void writeGetHeader(URL url, HTTPContext context, HTTPHeaders headers) throws IOException {
		OutputStream outputStream = context.transport().outputStream();
		
		StringBuilder headerBuilder = new StringBuilder();
		writeHTTPRequestLine(headerBuilder, url, "GET");
		writeHeaderFieldLines(headerBuilder, headers);
		writeCRLF(headerBuilder);

		outputStream.write(headerBuilder.toString().getBytes());
	}

	private static void writeHTTPRequestLine(StringBuilder headerBuilder, URL url, String method) {
		headerBuilder.append(method);
		headerBuilder.append(" ");
		headerBuilder.append(url.getPath());
		if (url.getQuery() != null) {
			headerBuilder.append("?");
			headerBuilder.append(url.getQuery());
		}
		headerBuilder.append(" ");
		headerBuilder.append(HTTP11Constants.HTTP11_NAME);
		writeCRLF(headerBuilder);
	}
	
	
	private static void writeHeaderFieldLines(StringBuilder headerBuilder, HTTPHeaders headers) {
		if (headers.has("Host")) {
			writeHeaderFieldLine(headerBuilder, "Host", headers.get("Host"));
		}
		for (HeaderEntry entry: headers) {
			if (entry.headerName().equals("Host")) {
				continue;
			}
			writeHeaderFieldLine(headerBuilder, entry.headerName(), entry.headerValue());
		}
	}
	
	private static void writeHeaderFieldLine(StringBuilder headerBuilder, String headerName, String headerValue) {
		headerBuilder.append(headerName.replaceAll("[\r\n\0]", " "));
		headerBuilder.append(": ");
		headerBuilder.append(headerValue.replaceAll("[\r\n\0]", " "));
		writeCRLF(headerBuilder);
	}

	private static void writeCRLF(StringBuilder headerBuilder) {
		headerBuilder.append("\r\n");
	}
}
