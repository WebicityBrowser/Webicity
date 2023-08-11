package com.github.webicitybrowser.webicitybrowser.gui.ui.menu;

import com.github.webicitybrowser.thready.color.Colors;
import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.drawing.core.Paint2D;
import com.github.webicitybrowser.thready.drawing.core.builder.Paint2DBuilder;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.directive.BackgroundColorDirective;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.webicitybrowser.gui.Styling;

public final class MenuButtonPainter {
	
	private MenuButtonPainter() {}

	public static void paint(MenuButtonUnit unit, LocalPaintContext localPaintContext) {
		paintBackground(unit, localPaintContext);
		paintText(unit, localPaintContext);
	}

	private static void paintBackground(MenuButtonUnit unit, LocalPaintContext localPaintContext) {
		Canvas2D canvas = localPaintContext.canvas();
		Rectangle documentRect = localPaintContext.documentRect();
		DirectivePool styleDirectives = unit.box().styleDirectives();
		
		ColorFormat color = getBackgroundColor(styleDirectives);
		Paint2D paint = Paint2DBuilder
			.clone(canvas.getPaint())
			.setColor(color)
			.build();
		
		Canvas2D ctx = canvas.withPaint(paint);
		ctx.drawRect(
			documentRect.position().x(),
			documentRect.position().y(),
			documentRect.size().width() - Styling.BUTTON_WIDTH / 2,
			documentRect.size().height());
		ctx.drawRect(
			documentRect.position().x(),
			documentRect.position().y(),
			documentRect.size().width(),
			documentRect.size().height() / 2);
		ctx.drawEllipse(
			documentRect.position().x() + documentRect.size().width() - Styling.BUTTON_WIDTH,
			documentRect.position().y(),
			Styling.BUTTON_WIDTH,
			documentRect.size().height());
	}
	
	private static void paintText(MenuButtonUnit unit, LocalPaintContext localPaintContext) {
		Canvas2D canvas = localPaintContext.canvas();
		Rectangle documentRect = localPaintContext.documentRect();
		Font2D font = unit.font();
		
		Paint2D paint = Paint2DBuilder
			.clone(canvas.getPaint())
			.setFont(font)
			.build();
		
		float textWidth = font.getMetrics().getStringWidth(Styling.PRODUCT_NAME);
		float textXOffset =
			(documentRect.size().width() - Styling.BUTTON_WIDTH / 2) / 2 -
			textWidth / 2;
		
		canvas
			.withPaint(paint)
			.drawText(
				documentRect.position().x() + textXOffset,
				documentRect.position().y(),
				Styling.PRODUCT_NAME);
	}

	private static ColorFormat getBackgroundColor(DirectivePool styleDirectives) {
		return styleDirectives
			.getDirectiveOrEmpty(BackgroundColorDirective.class)
			.map(directive -> directive.getColor())
			.orElse(Colors.TRANSPARENT);
	}

}
