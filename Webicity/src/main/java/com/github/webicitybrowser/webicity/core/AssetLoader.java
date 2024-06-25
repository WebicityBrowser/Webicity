package com.github.webicitybrowser.webicity.core;

import java.io.IOException;
import java.io.Reader;

public interface AssetLoader {

	Reader streamAsset(String assetType, String assetName) throws IOException;
	
}
