package com.github.webicitybrowser.webicitybrowser.gui.ui.button;

import com.github.webicitybrowser.thready.color.Colors;
import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.drawing.core.Paint2D;
import com.github.webicitybrowser.thready.drawing.core.builder.Paint2DBuilder;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.directive.BackgroundColorDirective;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;

public final class CircularButtonPainter {

	private CircularButtonPainter() {}

	public static void paint(CircularButtonUnit unit, LocalPaintContext localPaintContext) {
		paintBackground(unit, localPaintContext);
	}

	private static void paintBackground(CircularButtonUnit unit, LocalPaintContext localPaintContext) {
		Canvas2D canvas = localPaintContext.canvas();
		DirectivePool styleDirectives = unit.box().styleDirectives();
		Rectangle documentRect = localPaintContext.documentRect();
		
		ColorFormat color = getBackgroundColor(styleDirectives);
		Paint2D paint = Paint2DBuilder
			.clone(canvas.getPaint())
			.setColor(color)
			.build();
		
		float docX = documentRect.position().x();
		float docY = documentRect.position().y();
		float docW = documentRect.size().width();
		float docH = documentRect.size().height();
		
		canvas
			.withPaint(paint)
			.drawEllipse(docX, docY, docW, docH);
		
		canvas.drawTexture(
			docX + docW / 2 - 10,
			docY + docH / 2 - 10,
			20, 20, unit.image());
	}

	private static ColorFormat getBackgroundColor(DirectivePool styleDirectives) {
		return styleDirectives
			.getDirectiveOrEmpty(BackgroundColorDirective.class)
			.map(directive -> directive.getColor())
			.orElse(Colors.TRANSPARENT);
	}

}
