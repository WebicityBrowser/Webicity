package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.stage.paint;

import com.github.webicitybrowser.thready.color.Colors;
import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.drawing.core.Paint2D;
import com.github.webicitybrowser.thready.drawing.core.builder.Paint2DBuilder;
import com.github.webicitybrowser.thready.gui.graphical.directive.directive.BackgroundColorDirective;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.PaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.Painter;

public class BlockWrappingPainter implements Painter {

	private final Box box;
	private final Rectangle documentRect;
	private final Painter innerPainter;

	public BlockWrappingPainter(Box box, Rectangle documentRect, Painter innerPainter) {
		this.box = box;
		this.documentRect = documentRect;
		this.innerPainter = innerPainter;
	}

	@Override
	public void paint(PaintContext context, Canvas2D canvas) {
		paintBackground(canvas);
		paintInnerContent(context, canvas);
	}

	private void paintBackground(Canvas2D canvas) {
		Paint2D paint = new Paint2DBuilder()
			.setColor(getBackgroundColor())
			.build();
		canvas.setPaint(paint);
		canvas.drawRect(
			documentRect.position().x(),
			documentRect.position().y(),
			documentRect.size().width(),
			documentRect.size().height());
	}
	
	private void paintInnerContent(PaintContext context, Canvas2D canvas) {
		innerPainter.paint(context, canvas);
	}
	
	private ColorFormat getBackgroundColor() {
		return box
			.getStyleDirectives()
			.getDirectiveOrEmpty(BackgroundColorDirective.class)
			.map(directive -> directive.getColor())
			.orElse(Colors.TRANSPARENT);
	}

}
