package com.github.webicitybrowser.codec.png;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.codec.png.exception.MalformedPNGException;
import com.github.webicitybrowser.codec.png.imp.PNGReader;

public class PNGReaderTest {
	
	private PNGReader pngReader;

	@BeforeEach
	public void setup() {
		pngReader = new PNGReader();
	}

	@Test
	public void canNotParseBadHeaderPNG() {
		InputStream badHeaderStream = new ByteArrayInputStream(new byte[] { 2 });
		Assertions.assertThrows(MalformedPNGException.class, () -> pngReader.read(badHeaderStream));
	}

}
