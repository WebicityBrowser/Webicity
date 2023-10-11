package com.github.webicitybrowser.webicity.core;

import com.github.webicitybrowser.spec.fetch.FetchEngine;
import com.github.webicitybrowser.spec.fetch.connection.FetchConnection;
import com.github.webicitybrowser.spec.fetch.connection.FetchConnectionInfo;
import com.github.webicitybrowser.spec.fetch.connection.FetchConnectionPool;
import com.github.webicitybrowser.spec.fetch.connection.imp.HTTPFetchConnectionPool;
import com.github.webicitybrowser.spec.fetch.imp.FetchEngineImp;
import com.github.webicitybrowser.spec.url.URL;
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
	
	ProtocolRegistry getProtocolRegistry();
	
	RendererBackendRegistry getBackendRendererRegistry();
	
	public static RenderingEngine create(AssetLoader assetLoader) {
		return new RenderingEngineImp(assetLoader, new FetchEngineImp(new HTTPFetchConnectionPool()));
	}
	
}
