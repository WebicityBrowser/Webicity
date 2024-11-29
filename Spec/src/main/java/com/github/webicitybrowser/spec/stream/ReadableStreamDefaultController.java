package com.github.webicitybrowser.spec.stream;

public interface ReadableStreamDefaultController extends ReadableStreamController {
		
	// Extra methods
	void enqueue(Object chunk);

}
