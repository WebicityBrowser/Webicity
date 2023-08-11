package com.github.webicitybrowser.thready.gui.graphical.layout.core;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundRenderedUnit;

public record ChildLayoutResult(Rectangle relativeRect, BoundRenderedUnit<?> unit) {
	
}
