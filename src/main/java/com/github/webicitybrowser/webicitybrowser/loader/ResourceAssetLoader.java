package com.github.webicitybrowser.webicitybrowser.loader;

import java.io.IOException;
import java.io.Reader;

import com.github.webicitybrowser.webicity.core.AssetLoader;

public class ResourceAssetLoader implements AssetLoader {

	private final TypedAssetLoader staticLoader = new StaticAssetLoader();
	
	@Override
	public Reader streamAsset(String assetType, String assetName) throws IOException {
		return getTypedLoader(assetType).streamAsset(assetName);
	}

	private TypedAssetLoader getTypedLoader(String assetType) {
		switch (assetType) {
		case "static":
			return staticLoader;
		default:
			throw new UnsupportedOperationException("No such loader: " + assetType);
		}
	}

}
