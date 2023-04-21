package com.github.webicitybrowser.webicity.core;

import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.webicity.core.net.ProtocolRegistry;
import com.github.webicitybrowser.webicity.core.renderer.RendererBackendRegistry;
import com.github.webicitybrowser.webicity.core.renderer.RendererHandle;
import com.github.webicitybrowser.webicity.core.renderer.imp.RenderingEngineImp;

public interface RenderingEngine {

	RendererHandle openRenderer(URL url);
	
	AssetLoader getAssetLoader();
	
	ProtocolRegistry getProtocolRegistry();
	
	RendererBackendRegistry getBackendRendererRegistry();
	
	public static RenderingEngine create(AssetLoader assetLoader) {
		return new RenderingEngineImp(assetLoader);
	}
	
}
