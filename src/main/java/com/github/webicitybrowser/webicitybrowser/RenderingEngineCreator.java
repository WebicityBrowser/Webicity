package com.github.webicitybrowser.webicitybrowser;

import com.github.webicitybrowser.spec.http.HTTPService;
import com.github.webicitybrowser.spec.http.encoding.chunked.ChunkedEncoding;
import com.github.webicitybrowser.spec.http.version.http11.HTTP11Version;
import com.github.webicitybrowser.webicity.core.AssetLoader;
import com.github.webicitybrowser.webicity.core.RenderingEngine;
import com.github.webicitybrowser.webicity.core.net.ProtocolRegistry;
import com.github.webicitybrowser.webicity.protocol.AboutProtocol;
import com.github.webicitybrowser.webicity.protocol.FileProtocol;
import com.github.webicitybrowser.webicity.protocol.HTTPProtocol;
import com.github.webicitybrowser.webicity.renderer.backend.html.HTMLRendererBackend;
import com.github.webicitybrowser.webicitybrowser.loader.ResourceAssetLoader;
import com.github.webicitybrowser.webicitybrowser.net.SocketChannelHTTPTransportFactory;

public final class RenderingEngineCreator {

	public static RenderingEngine create() {
		AssetLoader assetLoader = new ResourceAssetLoader();
		RenderingEngine renderingEngine = RenderingEngine.create(assetLoader);
		
		registerProtocols(renderingEngine);
		registerBackendRenderers(renderingEngine);
		
		return renderingEngine;
	}

	private static void registerProtocols(RenderingEngine renderingEngine) {
		ProtocolRegistry protocolRegistry = renderingEngine.getProtocolRegistry();
		HTTPService httpService = createHTTPService();
		
		protocolRegistry.registerProtocol(new FileProtocol());
		protocolRegistry.registerProtocol(new AboutProtocol());
		protocolRegistry.registerProtocol(new HTTPProtocol(httpService));
	}

	private static void registerBackendRenderers(RenderingEngine renderingEngine) {
		renderingEngine.getBackendRendererRegistry()
			.registerBackendFactory("text/html", HTMLRendererBackend::new);
	}
	
	private static HTTPService createHTTPService() {
		HTTPService service = HTTPService.create("Webicity/0.1.0 ThreadyWeb/0.1.0 Firefox/113.0 (Not actually Firefox)");
		service.setTransportFactory(new SocketChannelHTTPTransportFactory());
		service.registerHTTPVersion(new HTTP11Version());
		service.registerTransferEncoding(new ChunkedEncoding());
		
		return service;
	}

}
