package com.github.webicitybrowser.webicitybrowser.loader;

import java.io.IOException;
import java.io.Reader;

public interface TypedAssetLoader {

	Reader streamAsset(String assetName) throws IOException;

}
