package com.github.webicitybrowser.spec.fetch.imp;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import com.github.webicitybrowser.spec.fetch.FetchRequest;
import com.github.webicitybrowser.spec.url.URL;

public class DataURLProcessor {

	public static Optional<DataURLStruct> processDataURL(FetchRequest request) {
		String input = getDataInput(request.url());
		String mimeType = determineMimeType(input);
		int position = mimeType.length();
		if (position >= input.length()) return Optional.empty();
		String encodedBody = input.substring(position + 1);
		String body = URLDecoder.decode(encodedBody, StandardCharsets.UTF_8);
		// TODO: Handle B64
		if (mimeType.startsWith(";")) mimeType = "text/plain" + mimeType;
		if (mimeType.toLowerCase().matches(".*; *base64$")) {
			// TODO: Handle B64
			return Optional.empty();
		}

		return Optional.of(new DataURLStruct(mimeType, body.getBytes(StandardCharsets.UTF_8)));
	}
	
	private static String determineMimeType(String input) {
		StringBuilder mimeTypeBuilder = new StringBuilder();
		for (int position = 0; position < input.length() && input.charAt(position) != ','; position++) {
			mimeTypeBuilder.appendCodePoint(input.codePointAt(position));
		}
		// TODO: Ensure only U+0020 is trimmed
		return  mimeTypeBuilder.toString().trim();
	}

	private static String getDataInput(URL url) {
		assert url.getScheme().equals("data");
		// TODO: Exclude fragment
		String input = url.toString();
		assert input.startsWith("data:");
		return input.substring(5);
	}

	public static record DataURLStruct(String mimeType, byte[] body) {}

}
