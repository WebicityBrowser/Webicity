package com.github.webicitybrowser.webicity.core.image.imp;

import java.util.HashMap;
import java.util.Map;

import com.github.webicitybrowser.codec.image.ImageCodec;
import com.github.webicitybrowser.webicity.core.image.ImageCodecRegistry;

public class ImageLoaderRegistryImp implements ImageCodecRegistry {

	private Map<String, ImageCodec> registeredImageLoaders = new HashMap<>();

	@Override
	public void registerImageLoader(ImageCodec imageLoader) {
		for (String type: imageLoader.getTypes()) {
			registeredImageLoaders.put(type, imageLoader);
		}
	}

	@Override
	public ImageCodec getImageLoaderForType(String type) {
		return registeredImageLoaders.get(type);
	}
	
}
