package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.drawing.core.Paint2D;
import com.github.webicitybrowser.thready.drawing.core.builder.Paint2DBuilder;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;

public final class ScrollPainter {

	private ScrollPainter() {}

	public static void paint(ScrollUnit unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		// TODO: Crop child document rect
		UIPipeline.paint(unit.innerUnit(), globalPaintContext, localPaintContext);
		paintVerticalScrollbar(unit, globalPaintContext, localPaintContext);
	}

	private static void paintVerticalScrollbar(ScrollUnit unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		Rectangle scrollbarLocation = ScrollUtils.getVerticalScrollbarLocation(unit, localPaintContext.documentRect());
		Paint2D paint = Paint2DBuilder.clone(localPaintContext.canvas().getPaint())
			.setColor(ScrollBarStyles.SCROLLBAR_COLOR)
			.build();
		Canvas2D canvas = localPaintContext.canvas()
			.withPaint(paint);
		canvas.drawEllipse(
			scrollbarLocation.position().x(),
			scrollbarLocation.position().y(),
			scrollbarLocation.size().width(),
			ScrollBarStyles.SCROLLBAR_INLINE_SIZE);
		canvas.drawRect(
			scrollbarLocation.position().x(),
			scrollbarLocation.position().y() + ScrollBarStyles.SCROLLBAR_INLINE_SIZE / 2,
			scrollbarLocation.size().width(),
			scrollbarLocation.size().height() - ScrollBarStyles.SCROLLBAR_INLINE_SIZE);
		canvas.drawEllipse(
			scrollbarLocation.position().x(),
			scrollbarLocation.position().y() + scrollbarLocation.size().height() - ScrollBarStyles.SCROLLBAR_INLINE_SIZE,
			scrollbarLocation.size().width(),
			ScrollBarStyles.SCROLLBAR_INLINE_SIZE);
	}

}
