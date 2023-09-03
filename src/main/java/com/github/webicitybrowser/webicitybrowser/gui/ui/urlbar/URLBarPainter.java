package com.github.webicitybrowser.webicitybrowser.gui.ui.urlbar;

import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.drawing.core.Paint2D;
import com.github.webicitybrowser.thready.drawing.core.builder.Paint2DBuilder;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.util.SimpleDirectiveUtil;
import com.github.webicitybrowser.thready.gui.graphical.view.textfield.TextFieldContext;
import com.github.webicitybrowser.thready.gui.graphical.view.textfield.TextFieldPainter;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebDirectiveUtil;
import com.github.webicitybrowser.webicitybrowser.gui.Styling;

public final class URLBarPainter {

	private URLBarPainter() {}
	
	public static void paint(URLBarUnit unit, GlobalPaintContext globalContext, LocalPaintContext localContext) {
		paintBackground(unit, localContext);
		paintForeground(unit, globalContext, localContext);
	}

	private static void paintBackground(URLBarUnit unit, LocalPaintContext context) {
		Canvas2D canvas = context.canvas();
		DirectivePool styleDirectives = unit.box().styleDirectives();
		Rectangle documentRect = context.documentRect();
		
		ColorFormat color = WebDirectiveUtil.getBackgroundColor(styleDirectives);
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
		
		ctx.drawEllipse(docX, docY, Styling.BUTTON_WIDTH, Styling.BUTTON_WIDTH);
		ctx.drawEllipse(docX + docW - Styling.BUTTON_WIDTH, docY, Styling.BUTTON_WIDTH, Styling.BUTTON_WIDTH);
	}
	
	private static void paintForeground(URLBarUnit unit, GlobalPaintContext globalContext, LocalPaintContext localContext) {
		Canvas2D canvas = localContext.canvas();
		DirectivePool styleDirectives = unit.box().styleDirectives();
		Rectangle contentRect = unit.getContentRect(localContext.documentRect());
		
		ColorFormat color = SimpleDirectiveUtil.getForegroundColor(styleDirectives);
		Paint2D paint = Paint2DBuilder
			.clone(canvas.getPaint())
			.setColor(color)
			.setFont(unit.font())
			.build();
		Canvas2D ctx = canvas.withPaint(paint);
		
		TextFieldContext textFieldContext = unit.context().textFieldContext();
		LocalPaintContext textFieldLocalContext = new LocalPaintContext(ctx, contentRect);
		TextFieldPainter.paint(textFieldContext, globalContext, textFieldLocalContext);
	}
	
}
