package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite;

import java.util.function.Supplier;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.CompositeLayer.CompositeReference;

public record CompositeParameters(CompositeReference reference, Supplier<AbsolutePosition> translate) {
	
}
