package com.github.webicitybrowser.webicitybrowser.gui.ui.menu;

import com.github.webicitybrowser.thready.color.Colors;
import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.drawing.core.Paint2D;
import com.github.webicitybrowser.thready.drawing.core.builder.Paint2DBuilder;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.graphical.directive.BackgroundColorDirective;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.PaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.Painter;
import com.github.webicitybrowser.webicitybrowser.gui.Styling;

public class MenuButtonPainter implements Painter {

	private final Box box;
	private final Rectangle documentRect;
	private final Font2D font;

	public MenuButtonPainter(Box box, Rectangle documentRect, Font2D font) {
		this.box = box;
		this.documentRect = documentRect;
		this.font = font;
	}

	@Override
	public void paint(PaintContext context, Canvas2D canvas, Rectangle viewportRect) {
		paintBackground(canvas);
		paintText(context, canvas);
	}

	private void paintBackground(Canvas2D canvas) {
		ColorFormat color = getBackgroundColor();
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
	
	private void paintText(PaintContext context, Canvas2D canvas) {
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

	private ColorFormat getBackgroundColor() {
		return box
			.getStyleDirectives()
			.getDirectiveOrEmpty(BackgroundColorDirective.class)
			.map(directive -> directive.getColor())
			.orElse(Colors.TRANSPARENT);
	}

}
