package com.github.webicitybrowser.webicitybrowser.loader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class StaticAssetLoader implements TypedAssetLoader {

	@Override
	public Reader streamAsset(String assetName) throws IOException {
		InputStream stream = ClassLoader.getSystemResource(assetName).openStream();
		InputStream bufferedStream = new BufferedInputStream(stream);
		Reader reader = new InputStreamReader(bufferedStream);
		return reader;
	}

}
