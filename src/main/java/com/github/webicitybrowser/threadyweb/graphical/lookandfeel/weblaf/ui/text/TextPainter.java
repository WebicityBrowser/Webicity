package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text;

import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.drawing.core.Paint2D;
import com.github.webicitybrowser.thready.drawing.core.builder.Paint2DBuilder;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebDirectiveUtil;

public final class TextPainter {

	private TextPainter() {}

	public static void paint(TextRenderedUnit unit, LocalPaintContext localPaintContext) {
		Canvas2D canvas = localPaintContext.canvas();
		Paint2D paint = createPaint(unit, canvas);
		Rectangle documentRect = localPaintContext.documentRect();
		
		canvas
			.withPaint(paint)
			.drawText(
				documentRect.position().x(),
				documentRect.position().y(),
				unit.text());
	}

	private static Paint2D createPaint(TextRenderedUnit unit, Canvas2D canvas) {
		return Paint2DBuilder.clone(canvas.getPaint())
			.setColor(getForegroundColor(unit))
			.setFont(unit.font())
			.build();
	}

	private static ColorFormat getForegroundColor(TextRenderedUnit unit) {
		DirectivePool styleDirectives = unit.styleDirectives();
		return WebDirectiveUtil.getForegroundColor(styleDirectives);
	}

}
