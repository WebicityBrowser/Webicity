package com.github.webicitybrowser.spec.fetch.imp;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import com.github.webicitybrowser.spec.infra.util.Base64Util;
import com.github.webicitybrowser.spec.url.URL;

public class DataURLProcessor {

	private DataURLProcessor() {}

	public static Optional<DataURLStruct> processDataURL(URL url) {
		String input = getDataInput(url);
		String mimeType = determineMimeType(input);
		int position = mimeType.length();
		if (position >= input.length()) return Optional.empty();
		String encodedBody = input.substring(position + 1);
		String body = URLDecoder.decode(encodedBody, StandardCharsets.UTF_8);
		if (mimeType.startsWith(";")) mimeType = "text/plain";
		byte[] decodedBody = null;
		if (mimeType.toLowerCase().matches(".*; *base64$")) {
			decodedBody = Base64Util.forgivingBase64Decode(body);
			if (decodedBody == null) return Optional.empty();
			mimeType = mimeType
				.substring(0, mimeType.length() - 6)
				.stripTrailing();
			mimeType = mimeType.substring(0, mimeType.length() - 1);
		} else {
			decodedBody = body.getBytes(StandardCharsets.UTF_8);
		}

		// TODO: Mime type record

		return Optional.of(new DataURLStruct(mimeType, decodedBody));
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
