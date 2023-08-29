package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.paint;

import com.github.webicitybrowser.thready.color.Colors;
import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.drawing.core.Paint2D;
import com.github.webicitybrowser.thready.drawing.core.builder.Paint2DBuilder;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.directive.BackgroundColorDirective;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;

public final class BackgroundPainter {

	private BackgroundPainter() {}

	public static void paintBackground(DirectivePool directives, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		paintColorBackground(directives, localPaintContext);
	}

	private static void paintColorBackground(DirectivePool directives, LocalPaintContext localPaintContext) {
		Canvas2D canvas = localPaintContext.canvas();
		Rectangle documentRect = localPaintContext.documentRect();
		Paint2D paint = new Paint2DBuilder()
			.setColor(getBackgroundColor(directives))
			.build();
		canvas
			.withPaint(paint)
			.drawRect(
				documentRect.position().x(),
				documentRect.position().y(),
				documentRect.size().width(),
				documentRect.size().height());
	}
	
	private static ColorFormat getBackgroundColor(DirectivePool directives) {
		return directives
			.getDirectiveOrEmpty(BackgroundColorDirective.class)
			.map(directive -> directive.getColor())
			.orElse(Colors.TRANSPARENT);
	}
	
}
