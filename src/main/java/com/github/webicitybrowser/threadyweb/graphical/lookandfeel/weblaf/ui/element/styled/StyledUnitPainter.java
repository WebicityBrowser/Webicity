package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.styled;

import com.github.webicitybrowser.thready.color.Colors;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.drawing.core.Paint2D;
import com.github.webicitybrowser.thready.drawing.core.builder.Paint2DBuilder;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.paint.BackgroundPainter;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.paint.BorderPainter;

public final class StyledUnitPainter {

	private static final boolean PAINT_DEBUG_RECTANGLES = false;

	private StyledUnitPainter() {}

	public static void paint(StyledUnit unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		if (PAINT_DEBUG_RECTANGLES) {
			paintDebugRectangle(localPaintContext);
		}

		BackgroundPainter.paintBackground(unit.context().styleDirectives(), globalPaintContext, localPaintContext);
		paintOutlines(unit, globalPaintContext, localPaintContext);
	}

	private static void paintOutlines(StyledUnit unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		float[] borders = unit.context().boxOffsetDimensions().borders();
		BorderPainter.paintLeftOutline(unit.context().styleDirectives(), globalPaintContext, localPaintContext, borders[0]);
		BorderPainter.paintRightOutline(unit.context().styleDirectives(), globalPaintContext, localPaintContext, borders[1]);
		BorderPainter.paintTopOutline(unit.context().styleDirectives(), globalPaintContext, localPaintContext, borders[2]);
		BorderPainter.paintBottomOutline(unit.context().styleDirectives(), globalPaintContext, localPaintContext, borders[3]);
	}

	private static void paintDebugRectangle(LocalPaintContext localPaintContext) {
		Rectangle documentRect = localPaintContext.documentRect();
		Paint2D paint = Paint2DBuilder.clone(localPaintContext.canvas().getPaint())
			.setColor(Colors.LIGHT_GRAY)
			.build();

		Canvas2D canvas = localPaintContext.canvas().withPaint(paint);
		
		// Draw a rectangle
		canvas.drawLine(
			documentRect.position().x(), documentRect.position().y(),
			documentRect.size().width(), 0);
		canvas.drawLine(
			documentRect.position().x(), documentRect.position().y(),
			0, documentRect.size().height());
		canvas.drawLine(
			documentRect.position().x() + documentRect.size().width(), documentRect.position().y(),
			0, documentRect.size().height());
		canvas.drawLine(
			documentRect.position().x(), documentRect.position().y() + documentRect.size().height(),
			documentRect.size().width(), 0);
	}

}
