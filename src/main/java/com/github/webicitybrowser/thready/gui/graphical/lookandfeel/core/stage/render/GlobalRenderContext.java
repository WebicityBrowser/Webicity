package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.ResourceLoader;
import com.github.webicitybrowser.thready.drawing.core.text.FontMetrics;

public interface GlobalRenderContext {

	AbsoluteSize viewportSize();

	ResourceLoader resourceLoader();
	
	FontMetrics rootFontMetrics();

}
