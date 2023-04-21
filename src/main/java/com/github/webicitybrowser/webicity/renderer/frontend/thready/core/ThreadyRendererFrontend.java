package com.github.webicitybrowser.webicity.renderer.frontend.thready.core;

import com.github.webicitybrowser.thready.windowing.core.ScreenContent;
import com.github.webicitybrowser.webicity.core.renderer.RendererFrontend;

public interface ThreadyRendererFrontend extends RendererFrontend {

	ScreenContent getContent();
	
}
