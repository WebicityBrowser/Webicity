package com.github.webicitybrowser.thready.gui.graphical.layout.core;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.CompositeLayer.CompositeReference;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public record ChildLayoutResult(RenderedUnit unit, Rectangle relativeRect, CompositeReference reference) {
	
	public ChildLayoutResult(RenderedUnit unit, Rectangle relativeRect) {
		this(unit, relativeRect, CompositeReference.PARENT);
	}

}
