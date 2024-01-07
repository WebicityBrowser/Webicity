package com.github.webicitybrowser.spec.infra;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.spec.infra.util.Base64Util;

public class Base64UtilTest {
	
	@Test
	@DisplayName("Can parse normal base64 string")
	public void testNormalBase64() {
		String initialBase64 = "SGVsbG8gV29ybGQh";
		byte[] decoded = Base64Util.forgivingBase64Decode(initialBase64);
		String decodedString = new String(decoded);
		Assertions.assertEquals("Hello World!", decodedString);
	}

	@Test
	@DisplayName("Can parse base64 with interlaced whitespace")
	public void canParseBase64WithInterlacedWhitespace() {
		String initialBase64 = "SGVs bG8gV 29ybGQh";
		byte[] decoded = Base64Util.forgivingBase64Decode(initialBase64);
		String decodedString = new String(decoded);
		Assertions.assertEquals("Hello World!", decodedString);
	}

	@Test
	@DisplayName("Can parse base64 with equal signs")
	public void canParseBase64WithEqualSigns() {
		String initialBase64 = "R29vZGJ5ZSBDcnVlbCBXb3JsZCE=";
		byte[] decoded = Base64Util.forgivingBase64Decode(initialBase64);
		String decodedString = new String(decoded);
		Assertions.assertEquals("Goodbye Cruel World!", decodedString);
	}

}
