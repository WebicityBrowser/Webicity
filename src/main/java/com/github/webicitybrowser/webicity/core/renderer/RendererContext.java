package com.github.webicitybrowser.webicity.core.renderer;

import com.github.webicitybrowser.spec.fetch.FetchEngine;
import com.github.webicitybrowser.webicity.core.AssetLoader;

public interface RendererContext {

	AssetLoader getAssetLoader();
	FetchEngine getFetchEngine();
	
	void crash(RendererCrashReason reason) throws RendererCrashException;
	
	void crash(Exception exception) throws RendererCrashException;
	
}
