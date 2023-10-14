package com.github.webicitybrowser.webicity.core.renderer;

import com.github.webicitybrowser.spec.fetch.FetchEngine;
import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.webicity.core.AssetLoader;

public interface RendererContext {

	AssetLoader getAssetLoader();

	FetchEngine getFetchEngine();

	URL getCurrentDocumentURL();
	
	void crash(RendererCrashReason reason) throws RendererCrashException;
	
	void crash(Exception exception) throws RendererCrashException;
	
}
