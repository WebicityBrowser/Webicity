package com.github.webicitybrowser.thready.windowing.core;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;

public interface ScreenContent {

	boolean redrawRequested();
	
	void redraw(Canvas2D canvas, AbsoluteSize size);
	
}
