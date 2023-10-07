package com.github.webicitybrowser.thready.gui.graphical.base.imp.stage.paint;

import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.CompositeLayer;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.thready.windowing.core.ScreenContent.ScreenContentRedrawContext;

public final class ContentPainter {
	
	private ContentPainter() {}

	public static void performPaintCycle(ScreenContentRedrawContext redrawContext, List<CompositeLayer> compositeLayers) {
		AbsoluteSize contentSize = redrawContext.contentSize();
		Canvas2D canvas = redrawContext.canvas();
		Rectangle viewport = new Rectangle(new AbsolutePosition(0, 0), contentSize);
		
		clearPaint(canvas, contentSize);

		for (CompositeLayer layer : compositeLayers) {
			layer.paint(
				new PaintContextImp(viewport, redrawContext.invalidationScheduler()),
				new LocalPaintContext(canvas, redrawContext.rootDocumentRect()));
		}
	}

	private static void clearPaint(Canvas2D canvas, AbsoluteSize contentSize) {
		// TODO: What if we have a transparent window?
		// The default paint is white
		canvas.drawRect(0, 0, contentSize.width(), contentSize.height());
	}

}
