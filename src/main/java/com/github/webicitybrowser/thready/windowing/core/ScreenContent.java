package com.github.webicitybrowser.thready.windowing.core;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.drawing.core.ResourceLoader;
import com.github.webicitybrowser.thready.gui.graphical.animation.InvalidationScheduler;
import com.github.webicitybrowser.thready.windowing.core.event.ScreenEvent;

public interface ScreenContent {

	boolean redrawRequested();
	
	void redraw(ScreenContentRedrawContext redrawContext);
	
	void handleEvent(ScreenEvent e, AbsoluteSize contentSize);
	
	public static record ScreenContentRedrawContext(
		Canvas2D canvas, AbsoluteSize contentSize, ResourceLoader resourceLoader, InvalidationScheduler invalidationScheduler
	) {
		public Rectangle rootDocumentRect() {
			return new Rectangle(AbsolutePosition.ZERO_POSITION, contentSize);
		}
	}
	
}
