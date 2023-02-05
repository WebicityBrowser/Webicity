package everyos.browser.webicitybrowser.gui.ui.button;

import everyos.desktop.thready.basic.directive.BackgroundColorDirective;
import everyos.desktop.thready.core.graphics.canvas.Canvas2D;
import everyos.desktop.thready.core.graphics.canvas.Paint2D;
import everyos.desktop.thready.core.graphics.canvas.imp.Paint2DBuilder;
import everyos.desktop.thready.core.graphics.color.Colors;
import everyos.desktop.thready.core.graphics.color.formats.ColorFormat;
import everyos.desktop.thready.core.graphics.image.LoadedImage;
import everyos.desktop.thready.core.gui.stage.box.Box;
import everyos.desktop.thready.core.gui.stage.paint.PaintContext;
import everyos.desktop.thready.core.gui.stage.paint.Painter;
import everyos.desktop.thready.core.positioning.Rectangle;

public class CircularButtonPainter implements Painter {

	private final Box box;
	private final Rectangle documentRect;
	private final LoadedImage image;

	public CircularButtonPainter(Box box, Rectangle documentRect, LoadedImage image) {
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
		
		float docX = documentRect.getPosition().getX();
		float docY = documentRect.getPosition().getY();
		float docW = documentRect.getSize().getWidth();
		float docH = documentRect.getSize().getHeight();
		
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
			.getDirectivePool()
			.getDirectiveOrEmpty(BackgroundColorDirective.class)
			.map(directive -> directive.getColor())
			.orElse(Colors.TRANSPARENT);
	}

}
