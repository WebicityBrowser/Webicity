package com.github.webicitybrowser.spec.fetch;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.spec.fetch.imp.DataURLProcessor;
import com.github.webicitybrowser.spec.fetch.imp.DataURLProcessor.DataURLStruct;
import com.github.webicitybrowser.spec.url.URL;

public class DataURLProcessorTest {
	
	@Test
	@DisplayName("Can parse percent encoded data URL")
	public void canParsePercentEncodedDataURL() {
		URL url = URL.ofSafe("data:;,Hello%2C%20World!");
		Optional<DataURLStruct> parsedDataOptional = DataURLProcessor.processDataURL(url);
		Assertions.assertTrue(parsedDataOptional.isPresent());
		DataURLStruct parsedData = parsedDataOptional.get();
		Assertions.assertEquals("text/plain", parsedData.mimeType());
		Assertions.assertArrayEquals("Hello, World!".getBytes(), parsedData.body());
	}

	@Test
	@DisplayName("Can parse base64 data URL")
	public void canParseBase64DataURL() {
		URL url = URL.ofSafe("data:text/html;base64,SGVsbG8sIFdvcmxkIQ%3D%3D");
		Optional<DataURLStruct> parsedDataOptional = DataURLProcessor.processDataURL(url);
		Assertions.assertTrue(parsedDataOptional.isPresent());
		DataURLStruct parsedData = parsedDataOptional.get();
		Assertions.assertEquals("text/html", parsedData.mimeType());
		Assertions.assertArrayEquals("Hello, World!".getBytes(), parsedData.body());
	}


}
