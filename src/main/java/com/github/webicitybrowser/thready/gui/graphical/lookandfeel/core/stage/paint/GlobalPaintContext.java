package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.animation.InvalidationScheduler;

public interface GlobalPaintContext {

	Rectangle viewport();

	InvalidationScheduler invalidationScheduler();
	
}
