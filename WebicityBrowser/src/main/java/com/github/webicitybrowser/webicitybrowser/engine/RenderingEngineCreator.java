package com.github.webicitybrowser.webicitybrowser.engine;

import java.io.InputStream;

import com.github.webicitybrowser.codec.jpeg.JPEGCodec;
import com.github.webicitybrowser.codec.png.PNGCodec;
import com.github.webicitybrowser.spec.html.parse.CharacterReferenceLookup;
import com.github.webicitybrowser.spec.http.HTTPService;
import com.github.webicitybrowser.spec.http.encoding.chunked.ChunkedEncoding;
import com.github.webicitybrowser.spec.http.version.http11.HTTP11Version;
import com.github.webicitybrowser.webicity.core.AssetLoader;
import com.github.webicitybrowser.webicity.core.RenderingEngine;
import com.github.webicitybrowser.webicity.core.image.ImageCodecRegistry;
import com.github.webicitybrowser.webicity.core.net.ProtocolRegistry;
import com.github.webicitybrowser.webicity.protocol.AboutProtocol;
import com.github.webicitybrowser.webicity.protocol.FileProtocol;
import com.github.webicitybrowser.webicity.protocol.HTTPProtocol;
import com.github.webicitybrowser.webicity.renderer.backend.html.HTMLRendererBackend;
import com.github.webicitybrowser.webicitybrowser.engine.file.FileUnicodeLookup;
import com.github.webicitybrowser.webicitybrowser.engine.net.SocketChannelHTTPTransportFactory;
import com.github.webicitybrowser.webicitybrowser.engine.protocol.WebicityProtocol;
import com.github.webicitybrowser.webicitybrowser.loader.ResourceAssetLoader;

public final class RenderingEngineCreator {

	public static RenderingEngine create() {
		HTTPService httpService = createHTTPService();
		AssetLoader assetLoader = new ResourceAssetLoader();
		RenderingEngine renderingEngine = RenderingEngine.create(assetLoader, httpService);
		
		registerProtocols(renderingEngine);
		registerBackendRenderers(renderingEngine);
		registerImageLoaders(renderingEngine);
		
		return renderingEngine;
	}

	private static void registerProtocols(RenderingEngine renderingEngine) {
		ProtocolRegistry protocolRegistry = renderingEngine.getProtocolRegistry();
		HTTPService httpService = createHTTPService();
		
		protocolRegistry.registerProtocol(new FileProtocol());
		protocolRegistry.registerProtocol(new WebicityProtocol());
		protocolRegistry.registerProtocol(new AboutProtocol());
		protocolRegistry.registerProtocol(new HTTPProtocol(httpService));
	}

	private static void registerBackendRenderers(RenderingEngine renderingEngine) {
		CharacterReferenceLookup characterReferenceLookup = createCharacterReferenceLookup();
		
		renderingEngine.getBackendRendererRegistry()
			.registerBackendFactory("text/html", (r, c) -> new HTMLRendererBackend(r, c, characterReferenceLookup));
	}

	private static void registerImageLoaders(RenderingEngine renderingEngine) {
		ImageCodecRegistry imageLoaderRegistry = renderingEngine.getImageLoaderRegistry();
		imageLoaderRegistry.registerImageLoader(new PNGCodec());
		imageLoaderRegistry.registerImageLoader(new JPEGCodec());
	}

	private static HTTPService createHTTPService() {
		HTTPService service = HTTPService.create("Webicity/0.1.0 ThreadyWeb/0.1.0 Firefox/113.0 (Not actually Firefox)");
		service.setTransportFactory(new SocketChannelHTTPTransportFactory());
		service.registerHTTPVersion(new HTTP11Version());
		service.registerTransferEncoding(new ChunkedEncoding());
		
		return service;
	}
	
	private static CharacterReferenceLookup createCharacterReferenceLookup() {
		InputStream resourceStream = ClassLoader.getSystemClassLoader().getResourceAsStream("renderer/html/charrefs.json");
		return new FileUnicodeLookup(resourceStream);
	}

}
