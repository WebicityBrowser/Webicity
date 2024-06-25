package com.github.webicitybrowser.codec.png.chunk.idat;

import com.github.webicitybrowser.codec.png.exception.UnsupportedPNGException;

public interface IDATImageRasterDecoder {

	byte[] decode(byte[] unfilteredData, int width, int height) throws UnsupportedPNGException;

}
