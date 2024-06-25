package com.github.webicitybrowser.webicity.core.image;

import com.github.webicitybrowser.codec.image.ImageCodec;

public interface ImageCodecRegistry {
	
	void registerImageLoader(ImageCodec imageLoader);

	ImageCodec getImageLoaderForType(String type);

}
