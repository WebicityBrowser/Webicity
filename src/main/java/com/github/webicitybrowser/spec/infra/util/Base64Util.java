package com.github.webicitybrowser.spec.infra.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class Base64Util {
	
	private Base64Util() {}

	public static byte[] forgivingBase64Decode(String input) {
		String normalizedInput = input.replaceAll("[\t\n\f\r ]", "");
		// Java's built-in B64 decoder already handles = padding
		if (!normalizedInput.matches("^[A-Za-z0-9+/]*={0,2}$")) return null;
		try {
			return Base64.getDecoder().decode(normalizedInput.getBytes(StandardCharsets.UTF_8));
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

}
