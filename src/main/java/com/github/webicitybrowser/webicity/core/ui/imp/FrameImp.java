package com.github.webicitybrowser.webicity.core.ui.imp;

import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.webicity.core.RenderingEngine;
import com.github.webicitybrowser.webicity.core.renderer.RendererHandle;
import com.github.webicitybrowser.webicity.core.ui.Frame;

public class FrameImp implements Frame {

	private final RenderingEngine renderingEngine;
	
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
		// TODO Auto-generated method stub
		return "Untitled Tab";
	}

	@Override
	public URL getURL() {
		return this.url;
	}

	@Override
	public void navigate(URL url) {
		this.url = url;
		this.currentRenderer = renderingEngine.openRenderer(url);
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

}
