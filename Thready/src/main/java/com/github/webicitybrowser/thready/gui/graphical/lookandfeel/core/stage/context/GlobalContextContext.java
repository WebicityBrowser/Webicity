package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context;

import com.github.webicitybrowser.thready.drawing.core.ResourceLoader;
import com.github.webicitybrowser.thready.drawing.core.text.FontMetrics;

public interface GlobalContextContext {

	ResourceLoader resourceLoader();
	
	FontMetrics rootFontMetrics();

}
