package com.github.webicitybrowser.webicitybrowser.gui.ui.tab;

import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.drawing.core.Paint2D;
import com.github.webicitybrowser.thready.drawing.core.builder.Paint2DBuilder;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.drawing.core.text.FontMetrics;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.PaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.Painter;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.util.SimpleDirectiveUtil;
import com.github.webicitybrowser.webicitybrowser.gui.Styling;
import com.github.webicitybrowser.webicitybrowser.gui.binding.component.tab.TabComponent;

public class TabPainter implements Painter {

	private final Box box;
	private final Rectangle documentRect;
	private final TabComponent component;
	private final Font2D font;

	public TabPainter(Box box, Rectangle documentRect, TabComponent component, Font2D font) {
		this.box = box;
		this.documentRect = documentRect;
		this.component = component;
		this.font = font;
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
		ColorFormat color = SimpleDirectiveUtil.getBackgroundColor(box.getStyleDirectives());
		Paint2D paint = Paint2DBuilder
			.clone(canvas.getPaint())
			.setColor(color)
			.build();
		
		float docX = documentRect.position().x();
		float docY = documentRect.position().y();
		float docW = documentRect.size().width();
		float docH = documentRect.size().height();
		
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
		ColorFormat color = SimpleDirectiveUtil.getBackgroundColor(box.getStyleDirectives());
		Paint2D paint = Paint2DBuilder
			.clone(canvas.getPaint())
			.setColor(color)
			.build();
		
		float docX = documentRect.position().x();
		float docY = documentRect.position().y();
		float docW = documentRect.size().width();
		float docH = documentRect.size().height();
		
		Canvas2D ctx = canvas.withPaint(paint);
		
		float circleY = docY + docH - Styling.BUTTON_WIDTH;
		ctx.drawRect(docX + Styling.BUTTON_WIDTH / 2, circleY, docW - Styling.BUTTON_WIDTH, Styling.BUTTON_WIDTH);
		ctx.drawEllipse(docX, circleY, Styling.BUTTON_WIDTH, Styling.BUTTON_WIDTH);
		ctx.drawEllipse(docX + docW - Styling.BUTTON_WIDTH, circleY, Styling.BUTTON_WIDTH, Styling.BUTTON_WIDTH);
	}

	private void paintText(PaintContext context, Canvas2D canvas) {
		ColorFormat color = SimpleDirectiveUtil.getForegroundColor(box.getStyleDirectives());
		Paint2D paint = Paint2DBuilder
			.clone(canvas.getPaint())
			.setColor(color)
			.setFont(font)
			.build();
		Canvas2D ctx = canvas.withPaint(paint);
		
		String text = component.getName();
		FontMetrics metrics = font.getMetrics();
		
		float docX = documentRect.position().x();
		float docY = documentRect.position().y();
		float docW = documentRect.size().width();
		float textOffsetX = docW / 2 - metrics.getStringWidth(text) / 2;
		
		ctx.drawText(docX + textOffsetX, docY, text);
	}
	
}
