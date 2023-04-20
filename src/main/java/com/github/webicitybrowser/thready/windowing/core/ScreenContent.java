package com.github.webicitybrowser.thready.windowing.core;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.drawing.core.ResourceLoader;

public interface ScreenContent {

	boolean redrawRequested();
	
	void redraw(ScreenContentRedrawContext redrawContext);
	
	public static record ScreenContentRedrawContext(Canvas2D canvas, AbsoluteSize contentSize, ResourceLoader resourceLoader) {
		
	}
	
}
