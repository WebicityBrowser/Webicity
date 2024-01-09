package com.github.webicitybrowser.webicity.core;

import com.github.webicitybrowser.spec.fetch.FetchEngine;
import com.github.webicitybrowser.spec.http.HTTPService;
import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.webicity.core.image.ImageCodecRegistry;
import com.github.webicitybrowser.webicity.core.net.ProtocolRegistry;
import com.github.webicitybrowser.webicity.core.renderer.RendererBackendRegistry;
import com.github.webicitybrowser.webicity.core.renderer.RendererHandle;
import com.github.webicitybrowser.webicity.core.renderer.imp.RenderingEngineImp;
import com.github.webicitybrowser.webicity.core.ui.Frame;

public interface RenderingEngine {

	RendererHandle openRenderer(URL url, Frame frame);
	
	Frame createFrame();
	
	AssetLoader getAssetLoader();

	FetchEngine getFetchEngine();

	HTTPService getHTTPService();

	ProtocolRegistry getProtocolRegistry();

	ImageCodecRegistry getImageLoaderRegistry();
	
	RendererBackendRegistry getBackendRendererRegistry();
	
	void tick();

	public static RenderingEngine create(AssetLoader assetLoader, HTTPService httpService) {
		return new RenderingEngineImp(assetLoader, httpService);
	}
	
}
