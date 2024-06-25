package com.github.webicitybrowser.webicitybrowser.gui.ui.frame;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.thready.windowing.core.ScreenContent.ScreenContentRedrawContext;

public final class FramePainter {
	
	private FramePainter() {}

	public static void paint(FrameUnit frameUnit, GlobalPaintContext context, LocalPaintContext localPaintContext) {
		Canvas2D childCanvas = localPaintContext.canvas();
		AbsoluteSize contentSize = localPaintContext.documentRect().size();
		ScreenContentRedrawContext redrawContext = new ScreenContentRedrawContext(
			childCanvas, contentSize, frameUnit.resourceLoader(),
			context.invalidationScheduler());
		frameUnit.screenContent().redraw(redrawContext);
	}

}
