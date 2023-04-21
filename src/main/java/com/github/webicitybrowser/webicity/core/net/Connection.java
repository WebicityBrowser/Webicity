package com.github.webicitybrowser.webicity.core.net;

import java.io.Reader;

public interface Connection {

	Reader getInputReader();
	
	String getContentType();
	
}
