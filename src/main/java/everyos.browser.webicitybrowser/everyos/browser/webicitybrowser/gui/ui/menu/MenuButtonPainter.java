package everyos.browser.webicitybrowser.gui.ui.menu;

import everyos.browser.webicitybrowser.gui.Styling;
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

public class MenuButtonPainter implements Painter {

	private final Box box;
	private final Rectangle documentRect;

	public MenuButtonPainter(Box box, Rectangle documentRect) {
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
		
		Canvas2D ctx = canvas.withPaint(paint);
		ctx.drawRect(
			documentRect.getPosition().getX(),
			documentRect.getPosition().getY(),
			documentRect.getSize().getWidth() - Styling.BUTTON_WIDTH/2,
			documentRect.getSize().getHeight());
		ctx.drawRect(
			documentRect.getPosition().getX(),
			documentRect.getPosition().getY(),
			documentRect.getSize().getWidth(),
			documentRect.getSize().getHeight()/2);
		ctx.drawEllipse(
			documentRect.getPosition().getX() + documentRect.getSize().getWidth() - Styling.BUTTON_WIDTH,
			documentRect.getPosition().getY(),
			Styling.BUTTON_WIDTH,
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
