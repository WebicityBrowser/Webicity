package com.github.webicitybrowser.webicitybrowser.gui.ui.tab;

import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.drawing.core.Paint2D;
import com.github.webicitybrowser.thready.drawing.core.builder.Paint2DBuilder;
import com.github.webicitybrowser.thready.drawing.core.text.FontMetrics;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.util.SimpleDirectiveUtil;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebStringUtils;
import com.github.webicitybrowser.webicitybrowser.gui.Styling;

public final class TabPainter {

	private TabPainter() {}

	public static void paint(TabUnit unit, LocalPaintContext localPaintContext) {
		Canvas2D canvas = localPaintContext.canvas();
		Rectangle documentRect = localPaintContext.documentRect();
		DirectivePool styleDirectives = unit.box().styleDirectives();
		
		boolean isTopLevel = unit.box().owningComponent().isTopLevel();
		if (isTopLevel) {
			paintTopLevelBackground(canvas, documentRect, styleDirectives);
		} else {
			paintLowerLevelBackground(canvas, documentRect, styleDirectives);
		}
		paintText(canvas, documentRect, styleDirectives, unit);
	}

	private static void paintTopLevelBackground(Canvas2D canvas, Rectangle documentRect, DirectivePool styleDirectives) {
		ColorFormat color = SimpleDirectiveUtil.getBackgroundColor(styleDirectives);
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
	
	private static void paintLowerLevelBackground(Canvas2D canvas, Rectangle documentRect, DirectivePool styleDirectives) {
		ColorFormat color = SimpleDirectiveUtil.getBackgroundColor(styleDirectives);
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

	private static void paintText(Canvas2D canvas, Rectangle documentRect, DirectivePool styleDirectives, TabUnit unit) {
		ColorFormat color = SimpleDirectiveUtil.getForegroundColor(styleDirectives);
		Paint2D paint = Paint2DBuilder
			.clone(canvas.getPaint())
			.setColor(color)
			.setFont(unit.font())
			.build();
		Canvas2D ctx = canvas.withPaint(paint);
		
		String text = unit.box().owningComponent().getName();
		FontMetrics metrics = unit.font().getMetrics();
		
		float docX = documentRect.position().x();
		float docY = documentRect.position().y();
		float docW = documentRect.size().width();
		float docH = documentRect.size().height();

		float expectedWidth = docW /* - Styling.BUTTON_WIDTH*/ - 2 * Styling.ELEMENT_PADDING;
		String trimmedText = WebStringUtils.trim(metrics, text, expectedWidth);

		float textOffsetX = docW / 2 - metrics.getStringWidth(trimmedText) / 2;
		float textOffsetY = docH / 2 - metrics.getCapHeight() / 2;
		
		ctx.drawText(docX + textOffsetX, docY + textOffsetY, trimmedText);
	}
	
}
