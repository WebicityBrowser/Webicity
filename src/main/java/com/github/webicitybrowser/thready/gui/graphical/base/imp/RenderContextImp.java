package com.github.webicitybrowser.thready.gui.graphical.base.imp;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.ResourceLoader;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;

public class RenderContextImp implements GlobalRenderContext {
	
	private final AbsoluteSize viewportSize;
	private final ResourceLoader resourceLoader;

	public RenderContextImp(AbsoluteSize viewportSize, ResourceLoader resourceLoader) {
		this.viewportSize = viewportSize;
		this.resourceLoader = resourceLoader;
	}

	@Override
	public AbsoluteSize getViewportSize() {
		return this.viewportSize;
	}

	@Override
	public ResourceLoader getResourceLoader() {
		return this.resourceLoader;
	}

}
