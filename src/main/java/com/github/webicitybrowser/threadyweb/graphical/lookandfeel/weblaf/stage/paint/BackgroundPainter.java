package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.paint;

import com.github.webicitybrowser.thready.color.Colors;
import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.drawing.core.Paint2D;
import com.github.webicitybrowser.thready.drawing.core.builder.Paint2DBuilder;
import com.github.webicitybrowser.thready.gui.graphical.directive.BackgroundColorDirective;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.PaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.Painter;

public class BackgroundPainter implements Painter {

	private final Box box;
	private final Rectangle documentRect;
	
	public BackgroundPainter(Box box, Rectangle documentRect) {
		this.box = box;
		this.documentRect = documentRect;
	}
	
	@Override
	public void paint(PaintContext context, Canvas2D canvas, Rectangle viewport) {
		paintBackground(canvas);
	}

	private void paintBackground(Canvas2D canvas) {
		Paint2D paint = new Paint2DBuilder()
			.setColor(getBackgroundColor())
			.build();
		canvas
			.withPaint(paint)
			.drawRect(
				documentRect.position().x(),
				documentRect.position().y(),
				documentRect.size().width(),
				documentRect.size().height());
	}
	
	private ColorFormat getBackgroundColor() {
		return box
			.getStyleDirectives()
			.getDirectiveOrEmpty(BackgroundColorDirective.class)
			.map(directive -> directive.getColor())
			.orElse(Colors.TRANSPARENT);
	}
	
}
