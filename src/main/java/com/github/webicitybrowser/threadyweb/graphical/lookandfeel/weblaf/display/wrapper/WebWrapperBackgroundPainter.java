package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display.wrapper;

import com.github.webicitybrowser.thready.color.Colors;
import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.drawing.core.Paint2D;
import com.github.webicitybrowser.thready.drawing.core.builder.Paint2DBuilder;
import com.github.webicitybrowser.thready.gui.graphical.directive.BackgroundColorDirective;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;

public final class WebWrapperBackgroundPainter {

	private WebWrapperBackgroundPainter() {}

	public static void paint(WebWrapperUnit<?> unit, LocalPaintContext localPaintContext) {
		paintBackground(unit, localPaintContext);
	}

	private static void paintBackground(WebWrapperUnit<?> unit, LocalPaintContext localPaintContext) {
		Canvas2D canvas = localPaintContext.canvas();
		Rectangle documentRect = localPaintContext.documentRect();
		Paint2D paint = new Paint2DBuilder()
			.setColor(getBackgroundColor(unit))
			.build();
		canvas
			.withPaint(paint)
			.drawRect(
				documentRect.position().x(),
				documentRect.position().y(),
				documentRect.size().width(),
				documentRect.size().height());
	}
	
	private static ColorFormat getBackgroundColor(WebWrapperUnit<?> unit) {
		return unit.box()
			.styleDirectives()
			.getDirectiveOrEmpty(BackgroundColorDirective.class)
			.map(directive -> directive.getColor())
			.orElse(Colors.TRANSPARENT);
	}
	
}
