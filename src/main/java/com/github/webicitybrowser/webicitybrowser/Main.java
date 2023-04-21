package com.github.webicitybrowser.webicitybrowser;

import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.thready.windowing.core.GraphicsSystem;
import com.github.webicitybrowser.thready.windowing.core.ScreenContent;
import com.github.webicitybrowser.thready.windowing.skija.SkijaGraphicsSystem;
import com.github.webicitybrowser.webicity.core.AssetLoader;
import com.github.webicitybrowser.webicity.core.RenderingEngine;
import com.github.webicitybrowser.webicity.core.renderer.ExceptionRendererCrashReason;
import com.github.webicitybrowser.webicity.core.renderer.RendererCrashReason;
import com.github.webicitybrowser.webicity.core.renderer.RendererHandle;
import com.github.webicitybrowser.webicity.protocol.FileProtocol;
import com.github.webicitybrowser.webicity.renderer.backend.html.HTMLRendererBackend;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.core.ThreadyRendererFrontend;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.ThreadyHTMLRendererFrontend;
import com.github.webicitybrowser.webicitybrowser.loader.ResourceAssetLoader;

public class Main {

	public static void main(String[] args) {
		String urlStr = args[0];
		URL url = URL.ofSafe(urlStr);
		RenderingEngine renderingEngine = createRenderingEngine();
		RendererHandle rendererHandle = renderingEngine.openRenderer(url);
		if (rendererHandle.getCrashReason().isPresent()) {
			logCrash(rendererHandle.getCrashReason().get());
			return;
		}
		HTMLRendererBackend backend = (HTMLRendererBackend) rendererHandle.getRenderer().get();
		ThreadyRendererFrontend frontend = backend.createFrontend(
			context -> new ThreadyHTMLRendererFrontend(backend, context));
		
		createGUIFor(frontend.getContent());
	}

	private static void logCrash(RendererCrashReason rendererCrashReason) {
		System.err.println("Renderer crashed! Error: " + rendererCrashReason.getTitle());
		if (rendererCrashReason instanceof ExceptionRendererCrashReason exceptionCrashReason) {
			exceptionCrashReason.getException().printStackTrace();
		}
	}

	private static RenderingEngine createRenderingEngine() {
		AssetLoader assetLoader = new ResourceAssetLoader();
		RenderingEngine renderingEngine = RenderingEngine.create(assetLoader);
		renderingEngine.getProtocolRegistry()
			.registerProtocol(new FileProtocol());
		renderingEngine.getBackendRendererRegistry()
			.registerBackendFactory("text/html", HTMLRendererBackend::new);
		
		return renderingEngine;
	}

	private static void createGUIFor(ScreenContent content) {
		GraphicsSystem graphicsSystem = SkijaGraphicsSystem.createDefault();
		
		graphicsSystem.createWindow(window -> {
			window
				.getScreen()
				.setScreenContent(content);
		});
	}
	
}
