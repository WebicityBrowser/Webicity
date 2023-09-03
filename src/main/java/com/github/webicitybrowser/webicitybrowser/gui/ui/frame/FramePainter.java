package com.github.webicitybrowser.webicitybrowser.gui.ui.frame;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.drawing.core.ChildCanvasSettings;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.thready.windowing.core.ScreenContent.ScreenContentRedrawContext;

public final class FramePainter {
	
	private FramePainter() {}

	public static void paint(FrameUnit frameUnit, GlobalPaintContext context, LocalPaintContext localPaintContext) {
		Canvas2D childCanvas = createChildCanvas(localPaintContext);
		AbsoluteSize contentSize = localPaintContext.documentRect().size();
		ScreenContentRedrawContext redrawContext = new ScreenContentRedrawContext(
			childCanvas, contentSize, frameUnit.resourceLoader(),
			context.invalidationScheduler());
		frameUnit.screenContent().redraw(redrawContext);
	}

	private static Canvas2D createChildCanvas(LocalPaintContext localPaintContext) {
		Rectangle documentRect = localPaintContext.documentRect();
		return localPaintContext.canvas().createClippedCanvas(
			documentRect.position().x(), documentRect.position().y(),
			documentRect.size().width(), documentRect.size().height(),
			new ChildCanvasSettings(false));
	}

}
