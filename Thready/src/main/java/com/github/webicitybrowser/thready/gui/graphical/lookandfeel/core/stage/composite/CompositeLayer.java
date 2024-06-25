package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;

public interface CompositeLayer {
	
	InvalidationLevel getInvalidationLevel();

	int getStackLevel();

	Rectangle getBounds();

	CompositeParameters getParameters();

	void paint(GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext, Rectangle viewport);

	static enum CompositeReference {
		PARENT, VIEWPORT, SCROLLPORT
	}

}
