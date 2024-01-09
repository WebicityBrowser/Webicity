package com.github.webicitybrowser.codec.image;

public interface ImageCodec {
	
	String[] getTypes();

	PossibleImage loadImage(byte[] data, ImageProgressiveCallback callback);

}
