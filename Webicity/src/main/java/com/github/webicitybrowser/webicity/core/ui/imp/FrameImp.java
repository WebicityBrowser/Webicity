package com.github.webicitybrowser.webicity.core.ui.imp;

import java.util.Optional;

import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.webicity.core.RenderingEngine;
import com.github.webicitybrowser.webicity.core.renderer.RendererHandle;
import com.github.webicitybrowser.webicity.core.ui.Frame;
import com.github.webicitybrowser.webicity.core.ui.FrameEventListener;
import com.github.webicitybrowser.webicity.event.EventDispatcher;
import com.github.webicitybrowser.webicity.event.imp.EventDispatcherImp;

public class FrameImp implements Frame {

	private final RenderingEngine renderingEngine;
	private final EventDispatcher<FrameEventListener> eventDispatcher = new EventDispatcherImp<>();
	
	private URL url;
	private RendererHandle currentRenderer;

	public FrameImp(RenderingEngine renderingEngine) {
		this.renderingEngine = renderingEngine;
		navigate(URL.ofSafe("about:blank"));
	}

	@Override
	public RendererHandle getCurrentRenderer() {
		return this.currentRenderer;
	}

	@Override
	public String getName() {
		Optional<String> rendererTitle = currentRenderer
			.getRenderer()
			.map(renderer -> renderer.getTitle());
		Optional<String> crashTitle = currentRenderer
			.getCrashReason()
			.map(reason -> reason.getTitle());
		return rendererTitle
			.or(() -> crashTitle)
			.orElse("Untitled Document");
	}

	@Override
	public URL getURL() {
		return this.url;
	}

	@Override
	public void navigate(URL url) {
		this.url = url;
		eventDispatcher.fire(listener -> listener.onURLChange(url));
		this.currentRenderer = renderingEngine.openRenderer(url, this);
		eventDispatcher.fire(listener -> listener.onRendererChange(currentRenderer));
	}
	
	@Override
	public boolean redirect(URL url) {
		this.url = url;
		eventDispatcher.fire(listener -> listener.onURLChange(url));
		
		return true;
	}

	@Override
	public void reload() {
		// TODO Auto-generated method stub

	}

	@Override
	public void back() {
		// TODO Auto-generated method stub

	}

	@Override
	public void forward() {
		// TODO Auto-generated method stub

	}

	@Override
	public void tick() {
		currentRenderer.tick();
	}

	@Override
	public void addEventListener(FrameEventListener listener, boolean sync) {
		eventDispatcher.addListener(listener);
		if (sync) {
			listener.onURLChange(url);
			listener.onRendererChange(currentRenderer);
		}
	}

}
