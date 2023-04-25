package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;

public interface Painter {

	void paint(PaintContext context, Canvas2D canvas, Rectangle viewport);
	
}
