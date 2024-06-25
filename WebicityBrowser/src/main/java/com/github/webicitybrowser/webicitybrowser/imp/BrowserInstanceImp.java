package com.github.webicitybrowser.webicitybrowser.imp;

import com.github.webicitybrowser.webicity.core.RenderingEngine;
import com.github.webicitybrowser.webicitybrowser.BrowserInstance;

public class BrowserInstanceImp implements BrowserInstance {

	private final RenderingEngine renderingEngine;

	public BrowserInstanceImp(RenderingEngine renderingEngine) {
		this.renderingEngine = renderingEngine;
	}

	@Override
	public RenderingEngine getRenderingEngine() {
		return this.renderingEngine;
	}

	@Override
	public void tick() {
		renderingEngine.tick();
	}

}
