package everyos.browser.webicitybrowser.gui.ui.urlbar;

import everyos.browser.webicitybrowser.gui.Styling;
import everyos.browser.webicitybrowser.gui.behavior.URLBarComponent;
import everyos.desktop.thready.core.graphics.canvas.Canvas2D;
import everyos.desktop.thready.core.graphics.canvas.Paint2D;
import everyos.desktop.thready.core.graphics.canvas.imp.Paint2DBuilder;
import everyos.desktop.thready.core.graphics.color.formats.ColorFormat;
import everyos.desktop.thready.core.graphics.text.LoadedFont;
import everyos.desktop.thready.core.gui.stage.box.Box;
import everyos.desktop.thready.core.gui.stage.paint.PaintContext;
import everyos.desktop.thready.core.gui.stage.paint.Painter;
import everyos.desktop.thready.core.positioning.Rectangle;
import everyos.desktop.thready.laf.simple.util.DirectiveUtil;

public class URLBarPainter implements Painter {

	private final Box box;
	private final Rectangle documentRect;
	private final URLBarComponent component;

	public URLBarPainter(Box box, Rectangle documentRect, URLBarComponent component) {
		this.box = box;
		this.documentRect = documentRect;
		this.component = component;
	}

	@Override
	public void paint(PaintContext context, Canvas2D canvas, Rectangle viewportRect) {
		paintBackground(canvas);
		paintText(context, canvas);
	}

	private void paintBackground(Canvas2D canvas) {
		ColorFormat color = DirectiveUtil.getBackgroundColor(box.getDirectivePool());
		Paint2D paint = Paint2DBuilder
			.clone(canvas.getPaint())
			.setColor(color)
			.build();
		
		float docX = documentRect.getPosition().getX();
		float docY = documentRect.getPosition().getY();
		float docW = documentRect.getSize().getWidth();
		float docH = documentRect.getSize().getHeight();
		
		Canvas2D ctx = canvas.withPaint(paint);
		
		ctx.drawRect(
			docX + Styling.BUTTON_WIDTH / 2, docY,
			docW - Styling.BUTTON_WIDTH, docH);
		
		ctx.drawEllipse(docX, docY, Styling.BUTTON_WIDTH, Styling.BUTTON_WIDTH);
		ctx.drawEllipse(docX + docW - Styling.BUTTON_WIDTH, docY, Styling.BUTTON_WIDTH, Styling.BUTTON_WIDTH);
	}
	
	private void paintText(PaintContext context, Canvas2D canvas) {
		ColorFormat color = DirectiveUtil.getForegroundColor(box.getDirectivePool());
		LoadedFont font = context
			.getResourceGenerator()
			.loadFont(DirectiveUtil.getFont(box.getDirectivePool()));
		Paint2D paint = Paint2DBuilder
			.clone(canvas.getPaint())
			.setColor(color)
			.setLoadedFont(font)
			.build();
		Canvas2D ctx = canvas.withPaint(paint);
		
		String text = component.getValue();
		
		float docX = documentRect.getPosition().getX();
		float docY = documentRect.getPosition().getY();
		float textOffsetX = Styling.BUTTON_WIDTH / 2;
		
		ctx.drawText(docX + textOffsetX, docY, text);
	}
	
}
