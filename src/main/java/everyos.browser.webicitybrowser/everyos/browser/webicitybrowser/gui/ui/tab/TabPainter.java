package everyos.browser.webicitybrowser.gui.ui.tab;

import everyos.browser.webicitybrowser.gui.Styling;
import everyos.browser.webicitybrowser.gui.binding.component.tab.TabComponent;
import everyos.desktop.thready.core.graphics.canvas.Canvas2D;
import everyos.desktop.thready.core.graphics.canvas.Paint2D;
import everyos.desktop.thready.core.graphics.canvas.imp.Paint2DBuilder;
import everyos.desktop.thready.core.graphics.color.formats.ColorFormat;
import everyos.desktop.thready.core.graphics.text.FontMetrics;
import everyos.desktop.thready.core.graphics.text.LoadedFont;
import everyos.desktop.thready.core.gui.stage.box.Box;
import everyos.desktop.thready.core.gui.stage.paint.PaintContext;
import everyos.desktop.thready.core.gui.stage.paint.Painter;
import everyos.desktop.thready.core.positioning.Rectangle;
import everyos.desktop.thready.laf.simple.util.DirectiveUtil;

public class TabPainter implements Painter {

	private final Box box;
	private final Rectangle documentRect;
	private final TabComponent component;

	public TabPainter(Box box, Rectangle documentRect, TabComponent component) {
		this.box = box;
		this.documentRect = documentRect;
		this.component = component;
	}

	@Override
	public void paint(PaintContext context, Canvas2D canvas, Rectangle viewportRect) {
		if (component.isTopLevel()) {
			paintTopLevelBackground(canvas);
		} else {
			paintLowerLevelBackground(canvas);
		}
		paintText(context, canvas);
	}

	private void paintTopLevelBackground(Canvas2D canvas) {
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
		ctx.drawRect(
			docX, docY,
			docW, docH - Styling.BUTTON_WIDTH / 2);
		
		float circleY = docY + docH - Styling.BUTTON_WIDTH;
		ctx.drawEllipse(docX, circleY, Styling.BUTTON_WIDTH, Styling.BUTTON_WIDTH);
		ctx.drawEllipse(docX + docW - Styling.BUTTON_WIDTH, circleY, Styling.BUTTON_WIDTH, Styling.BUTTON_WIDTH);
	}
	
	private void paintLowerLevelBackground(Canvas2D canvas) {
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
		
		float circleY = docY + docH - Styling.BUTTON_WIDTH;
		ctx.drawRect(docX + Styling.BUTTON_WIDTH / 2, circleY, docW - Styling.BUTTON_WIDTH, Styling.BUTTON_WIDTH);
		ctx.drawEllipse(docX, circleY, Styling.BUTTON_WIDTH, Styling.BUTTON_WIDTH);
		ctx.drawEllipse(docX + docW - Styling.BUTTON_WIDTH, circleY, Styling.BUTTON_WIDTH, Styling.BUTTON_WIDTH);
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
		
		String text = component.getName();
		FontMetrics metrics = font.getMetrics();
		
		float docX = documentRect.getPosition().getX();
		float docY = documentRect.getPosition().getY();
		float docW = documentRect.getSize().getWidth();
		float textOffsetX = docW / 2 - metrics.getStringWidth(text) / 2;
		
		ctx.drawText(docX + textOffsetX, docY, text);
	}
	
}
