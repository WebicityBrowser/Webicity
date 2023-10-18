package com.github.webicitybrowser.webicity.core.image.imp;

import java.util.HashMap;
import java.util.Map;

import com.github.webicitybrowser.webicity.core.image.ImageLoader;
import com.github.webicitybrowser.webicity.core.image.ImageLoaderRegistry;

public class ImageLoaderRegistryImp implements ImageLoaderRegistry {

	private Map<String, ImageLoader> registeredImageLoaders = new HashMap<>();

	@Override
	public void registerImageLoader(ImageLoader imageLoader) {
		for (String type: imageLoader.getTypes()) {
			registeredImageLoaders.put(type, imageLoader);
		}
	}

	@Override
	public ImageLoader getImageLoaderForType(String type) {
		return registeredImageLoaders.get(type);
	}
	
}
