package everyos.browser.webicitybrowser.gui.ui.button;

import everyos.desktop.thready.basic.directive.BackgroundColorDirective;
import everyos.desktop.thready.core.graphics.canvas.Canvas2D;
import everyos.desktop.thready.core.graphics.canvas.Paint2D;
import everyos.desktop.thready.core.graphics.canvas.imp.Paint2DBuilder;
import everyos.desktop.thready.core.graphics.color.Colors;
import everyos.desktop.thready.core.graphics.color.formats.ColorFormat;
import everyos.desktop.thready.core.gui.stage.box.Box;
import everyos.desktop.thready.core.gui.stage.paint.PaintContext;
import everyos.desktop.thready.core.gui.stage.paint.Painter;
import everyos.desktop.thready.core.positioning.Rectangle;

public class CircularButtonPainter implements Painter {

	private final Box box;
	private final Rectangle documentRect;

	public CircularButtonPainter(Box box, Rectangle documentRect) {
		this.box = box;
		this.documentRect = documentRect;
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
		
		canvas
			.withPaint(paint)
			.drawEllipse(
				documentRect.getPosition().getX(),
				documentRect.getPosition().getY(),
				documentRect.getSize().getWidth(),
				documentRect.getSize().getHeight());
	}

	private ColorFormat getBackgroundColor() {
		return box
			.getDirectivePool()
			.getDirectiveOrEmpty(BackgroundColorDirective.class)
			.map(directive -> directive.getColor())
			.orElse(Colors.TRANSPARENT);
	}

}
