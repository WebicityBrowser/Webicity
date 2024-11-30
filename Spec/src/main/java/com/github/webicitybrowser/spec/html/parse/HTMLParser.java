package com.github.webicitybrowser.spec.html.parse;

import java.io.IOException;

public interface HTMLParser {

	void next(byte[] byteBuffer) throws IOException;

	void done() throws IOException;
	
}
