package com.github.webicitybrowser.webicity.core.image;

public interface ImageLoaderRegistry {
	
	void registerImageLoader(ImageLoader imageLoader);

	ImageLoader getImageLoaderForType(String type);

}
