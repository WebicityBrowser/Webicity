package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint;

import com.github.webicitybrowser.thready.gui.graphical.animation.InvalidationScheduler;

public interface GlobalPaintContext {

	InvalidationScheduler invalidationScheduler();

	static GlobalPaintContext create(InvalidationScheduler invalidationScheduler) {
		return () -> invalidationScheduler;
	}
	
}
