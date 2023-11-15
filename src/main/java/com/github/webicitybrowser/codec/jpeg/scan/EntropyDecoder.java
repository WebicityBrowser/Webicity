package com.github.webicitybrowser.codec.jpeg.scan;

public interface EntropyDecoder {

	void next(byte value);

	void done();

	int[] getDecoded();

	void restart();

}
