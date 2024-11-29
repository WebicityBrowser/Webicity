package com.github.webicitybrowser.spec.stream;

import com.github.webicitybrowser.spec.stream.imp.ReadableStreamImp;

public interface ReadableStream {

	// Extra methods
	void enqueue(Object chunk);

	void close();

	ReadableStreamController controller();

	void setReader(ReadableStreamReader reader);

	boolean isLocked();

	State getState();
	
	static ReadableStream create() {
		return new ReadableStreamImp();
	}

	static enum State {
		READABLE, CLOSED, ERRORED
	}

}
