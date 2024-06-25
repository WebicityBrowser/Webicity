package com.github.webicitybrowser.thready.drawing.core.image;

public record RasterBytesImageSource(int width, int height, byte[] raster) implements ImageSource {
	
}
