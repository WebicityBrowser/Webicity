package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.animation.InvalidationScheduler;

public interface GlobalPaintContext {

	Rectangle viewport();

	InvalidationScheduler invalidationScheduler();

	static GlobalPaintContext create(Rectangle viewport, InvalidationScheduler invalidationScheduler) {
		return new GlobalPaintContext() {
			@Override
			public Rectangle viewport() {
				return viewport;
			}

			@Override
			public InvalidationScheduler invalidationScheduler() {
				return invalidationScheduler;
			}
		};
	}
	
}
