package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.display.wrapper;

import com.github.webicitybrowser.thready.color.Colors;
import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.drawing.core.Paint2D;
import com.github.webicitybrowser.thready.drawing.core.builder.Paint2DBuilder;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.directive.BackgroundColorDirective;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public class SimpleWrapperPainter {

	public static <V extends RenderedUnit> void paint(
		SimpleWrapperUnit<V> unit, UIDisplay<?, ?, V> childDisplay, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext
	) {
		paintBackground(unit, localPaintContext);
	}

	private static <V extends RenderedUnit> void paintBackground(SimpleWrapperUnit<V> unit, LocalPaintContext localPaintContext) {
		Paint2D paint = new Paint2DBuilder()
			.setColor(getBackgroundColor(unit.styleDirectives()))
			.build();
		Canvas2D canvas = localPaintContext.canvas();
		Rectangle documentRect = localPaintContext.documentRect();
		
		canvas
			.withPaint(paint)
			.drawRect(
				documentRect.position().x(),
				documentRect.position().y(),
				documentRect.size().width(),
				documentRect.size().height());
	}
	
	private static ColorFormat getBackgroundColor(DirectivePool styleDirectives) {
		return styleDirectives
			.getDirectiveOrEmpty(BackgroundColorDirective.class)
			.map(directive -> directive.getColor())
			.orElse(Colors.TRANSPARENT);
	}

}
