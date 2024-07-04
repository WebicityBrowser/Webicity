package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.style;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.ResourceLoader;
import com.github.webicitybrowser.thready.drawing.core.text.FontMetrics;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeel;

public record StyleContext(AbsoluteSize viewportSize, ResourceLoader resourceLoader, FontMetrics rootFontMetrics, LookAndFeel lookAndFeel) {
	
}
