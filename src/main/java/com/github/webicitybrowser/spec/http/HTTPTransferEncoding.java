package com.github.webicitybrowser.spec.http;

import java.io.InputStream;

public interface HTTPTransferEncoding {
	
	String getName();

	InputStream decode(InputStream inputStream);
	
}
