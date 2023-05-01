package com.github.webicitybrowser.webicitybrowser.gui.ui.button;

import com.github.webicitybrowser.thready.color.Colors;
import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.drawing.core.Paint2D;
import com.github.webicitybrowser.thready.drawing.core.builder.Paint2DBuilder;
import com.github.webicitybrowser.thready.drawing.core.image.Image;
import com.github.webicitybrowser.thready.gui.graphical.directive.BackgroundColorDirective;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.PaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.Painter;

public class CircularButtonPainter implements Painter {

	private final Box box;
	private final Rectangle documentRect;
	private final Image image;

	public CircularButtonPainter(Box box, Rectangle documentRect, Image image) {
		this.box = box;
		this.documentRect = documentRect;
		this.image = image;
	}

	@Override
	public void paint(PaintContext context, Canvas2D canvas, Rectangle viewportRect) {
		paintBackground(canvas);
	}

	private void paintBackground(Canvas2D canvas) {
		ColorFormat color = getBackgroundColor();
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
			20, 20, image);
	}

	private ColorFormat getBackgroundColor() {
		return box
			.getStyleDirectives()
			.getDirectiveOrEmpty(BackgroundColorDirective.class)
			.map(directive -> directive.getColor())
			.orElse(Colors.TRANSPARENT);
	}

}
