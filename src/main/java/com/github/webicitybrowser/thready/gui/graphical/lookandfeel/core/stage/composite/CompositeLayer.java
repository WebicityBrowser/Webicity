package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;

public interface CompositeLayer {
	
	InvalidationLevel getInvalidationLevel();

	Rectangle getBounds();

	CompositeReference getReference();

	void paint(GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext);

	static enum CompositeReference {
		PAGE, VIEWPORT, SCROLLPORT, SCROLLROOT
	}

}
