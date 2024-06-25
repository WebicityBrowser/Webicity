package com.github.webicitybrowser.webicity.renderer.backend.html;

import com.github.webicitybrowser.spec.htmlbrowsers.tasks.EventLoop;
import com.github.webicitybrowser.webicity.core.renderer.RendererContext;

public record HTMLRendererContext(RendererContext rendererContext, EventLoop eventLoop) {
	
}
