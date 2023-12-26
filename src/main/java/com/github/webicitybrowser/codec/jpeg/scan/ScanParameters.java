package com.github.webicitybrowser.codec.jpeg.scan;

import java.io.PushbackInputStream;

public record ScanParameters(PushbackInputStream dataStream, ScanComponent[] scanComponents) {
	
}
