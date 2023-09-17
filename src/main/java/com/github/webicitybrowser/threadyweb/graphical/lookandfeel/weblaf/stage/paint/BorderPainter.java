package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.paint;

import com.github.webicitybrowser.thready.color.Colors;
import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.drawing.core.Paint2D;
import com.github.webicitybrowser.thready.drawing.core.builder.Paint2DBuilder;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.directive.ForegroundColorDirective;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderColorDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderColorDirective.LeftBorderColorDirective;

public class BorderPainter {
	
	private BorderPainter() {}
	
	public static void paintLeftOutline(DirectivePool directives, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext, float width) {
		Canvas2D canvas = createCanvas(directives, localPaintContext, LeftBorderColorDirective.class);
	
		Rectangle documentRect = localPaintContext.documentRect();
		canvas.drawRect(
			documentRect.position().x(), documentRect.position().y(),
			width, documentRect.size().height());
	}

	public static void paintTopOutline(DirectivePool directives, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext, float width) {
		Canvas2D canvas = createCanvas(directives, localPaintContext, BorderColorDirective.TopBorderColorDirective.class);
	
		Rectangle documentRect = localPaintContext.documentRect();
		canvas.drawRect(
			documentRect.position().x(), documentRect.position().y(),
			documentRect.size().width(), width);
	}

	public static void paintRightOutline(DirectivePool directives, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext, float width) {
		Canvas2D canvas = createCanvas(directives, localPaintContext, BorderColorDirective.RightBorderColorDirective.class);
	
		Rectangle documentRect = localPaintContext.documentRect();
		canvas.drawRect(
			documentRect.position().x() + documentRect.size().width() - width, documentRect.position().y(),
			width, documentRect.size().height());
	}

	public static void paintBottomOutline(DirectivePool directives, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext, float width) {
		Canvas2D canvas = createCanvas(directives, localPaintContext, BorderColorDirective.BottomBorderColorDirective.class);
	
		Rectangle documentRect = localPaintContext.documentRect();
		canvas.drawRect(
			documentRect.position().x(), documentRect.position().y() + documentRect.size().height() - width,
			documentRect.size().width(), width);
	}

	private static Canvas2D createCanvas(DirectivePool directives, LocalPaintContext localPaintContext, Class<? extends BorderColorDirective> directiveClass) {
		Canvas2D canvas = localPaintContext.canvas();
		ColorFormat color = directives
			.getDirectiveOrEmpty(directiveClass)
			.map(directive -> directive.getColor())
			.or(() -> directives
				.getDirectiveOrEmpty(ForegroundColorDirective.class)
				.map(directive -> directive.getColor()))
			.orElse(Colors.GREEN);
		Paint2D paint = Paint2DBuilder.clone(canvas.getPaint())
			.setColor(color)
			.build();

		return canvas.withPaint(paint);
	}

}
