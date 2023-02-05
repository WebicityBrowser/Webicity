package everyos.browser.webicitybrowser.gui.ui.menu;

import everyos.browser.webicitybrowser.gui.Styling;
import everyos.desktop.thready.basic.directive.BackgroundColorDirective;
import everyos.desktop.thready.core.graphics.canvas.Canvas2D;
import everyos.desktop.thready.core.graphics.canvas.Paint2D;
import everyos.desktop.thready.core.graphics.canvas.imp.Paint2DBuilder;
import everyos.desktop.thready.core.graphics.color.Colors;
import everyos.desktop.thready.core.graphics.color.formats.ColorFormat;
import everyos.desktop.thready.core.graphics.text.FontInfo;
import everyos.desktop.thready.core.graphics.text.LoadedFont;
import everyos.desktop.thready.core.gui.stage.box.Box;
import everyos.desktop.thready.core.gui.stage.paint.PaintContext;
import everyos.desktop.thready.core.gui.stage.paint.Painter;
import everyos.desktop.thready.core.positioning.Rectangle;
import everyos.desktop.thready.laf.simple.util.DirectiveUtil;

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
			documentRect.getPosition().getX(),
			documentRect.getPosition().getY(),
			documentRect.getSize().getWidth() - Styling.BUTTON_WIDTH / 2,
			documentRect.getSize().getHeight());
		ctx.drawRect(
			documentRect.getPosition().getX(),
			documentRect.getPosition().getY(),
			documentRect.getSize().getWidth(),
			documentRect.getSize().getHeight() / 2);
		ctx.drawEllipse(
			documentRect.getPosition().getX() + documentRect.getSize().getWidth() - Styling.BUTTON_WIDTH,
			documentRect.getPosition().getY(),
			Styling.BUTTON_WIDTH,
			documentRect.getSize().getHeight());
	}
	
	private void paintText(PaintContext context, Canvas2D canvas) {
		LoadedFont font = loadFont(context);
		Paint2D paint = Paint2DBuilder
			.clone(canvas.getPaint())
			.setLoadedFont(font)
			.build();
		
		float textWidth = font.getMetrics().getStringWidth(Styling.PRODUCT_NAME);
		float textXOffset =
			(documentRect.getSize().getWidth() - Styling.BUTTON_WIDTH / 2) / 2 -
			textWidth / 2;
		
		canvas
			.withPaint(paint)
			.drawText(
				documentRect.getPosition().getX() + textXOffset,
				documentRect.getPosition().getY(),
				Styling.PRODUCT_NAME);
	}

	private LoadedFont loadFont(PaintContext context) {
		FontInfo fontInfo = DirectiveUtil.getFont(box.getDirectivePool());
		return context
			.getResourceGenerator()
			.loadFont(fontInfo);
	}

	private ColorFormat getBackgroundColor() {
		return box
			.getDirectivePool()
			.getDirectiveOrEmpty(BackgroundColorDirective.class)
			.map(directive -> directive.getColor())
			.orElse(Colors.TRANSPARENT);
	}

}
