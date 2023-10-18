package com.github.webicitybrowser.webicity.core.image;

public interface ImageLoader {
	
	String[] getTypes();

	ImageData loadImage(byte[] data);

}
