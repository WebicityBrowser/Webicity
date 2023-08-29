package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.ResourceLoader;

public interface GlobalRenderContext {

	AbsoluteSize getViewportSize();

	ResourceLoader getResourceLoader();
	
}
