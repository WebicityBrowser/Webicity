package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.image.alt;

import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.drawing.core.Paint2D;
import com.github.webicitybrowser.thready.drawing.core.builder.Paint2DBuilder;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.directive.WebDirectiveUtil;

public final class ImageAltPainter {

	private ImageAltPainter() {}

	public static void paint(ImageAltUnit unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		Canvas2D canvas = localPaintContext.canvas();
		Paint2D paint = createPaint(unit, canvas);
		Rectangle documentRect = localPaintContext.documentRect();
		
		canvas
			.withPaint(paint)
			.drawText(
				documentRect.position().x(),
				documentRect.position().y(),
				unit.altText());
	}

	private static Paint2D createPaint(ImageAltUnit unit, Canvas2D canvas) {
		return Paint2DBuilder.clone(canvas.getPaint())
			.setColor(getForegroundColor(unit))
			.setFont(unit.font())
			.build();
	}

	private static ColorFormat getForegroundColor(ImageAltUnit unit) {
		DirectivePool styleDirectives = unit.styleDirectives();
		return WebDirectiveUtil.getForegroundColor(styleDirectives);
	}

}
