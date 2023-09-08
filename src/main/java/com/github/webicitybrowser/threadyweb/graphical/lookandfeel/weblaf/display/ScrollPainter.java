package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.util.AbsolutePositionMath;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.drawing.core.Paint2D;
import com.github.webicitybrowser.thready.drawing.core.builder.Paint2DBuilder;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;

public final class ScrollPainter {

	private ScrollPainter() {}

	public static void paint(ScrollUnit unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		// TODO: Crop child document rect
		paintChild(unit, globalPaintContext, localPaintContext);
		paintScrollbar(unit, globalPaintContext, localPaintContext, ScrollbarStyles.VERTICAL_SCROLLBAR);
		paintScrollbar(unit, globalPaintContext, localPaintContext, ScrollbarStyles.HORIZONTAL_SCROLLBAR);
	}

	private static void paintChild(ScrollUnit unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		AbsolutePosition scrollPosition = unit.box().scrollContext().scrollPosition();

		Canvas2D childCanvas = localPaintContext.canvas().createTranslatedCanvas(scrollPosition.x(), scrollPosition.y());
		Rectangle childDocumentRect = new Rectangle(
			localPaintContext.documentRect().position(),
			unit.innerUnitSize());
		LocalPaintContext childLocalPaintContext = new LocalPaintContext(childCanvas, childDocumentRect);

		Rectangle viewport = globalPaintContext.viewport();
		Rectangle childViewport = new Rectangle(
			AbsolutePositionMath.sum(viewport.position(), scrollPosition),
			viewport.size());
		GlobalPaintContext childGlobalPaintContext = GlobalPaintContext.create(
			childViewport,
			globalPaintContext.invalidationScheduler());
		
		UIPipeline.paint(unit.innerUnit(), childGlobalPaintContext, childLocalPaintContext);
	}

	private static void paintScrollbar(
		ScrollUnit unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext, ScrollbarDimensionTranslator positionTranslator
	) {
		Rectangle scrollbarLocation = ScrollUtils.getScrollbarLocation(unit, localPaintContext.documentRect(), positionTranslator);

		if (scrollbarLocation.size().height() == ScrollbarStyles.NOT_PRESENT) return;

		AbsolutePosition translatedScrollPosition = positionTranslator.translateToUpright(scrollbarLocation.position());
		AbsoluteSize translatedScrollSize = positionTranslator.translateToUpright(scrollbarLocation.size());


		Paint2D paint = Paint2DBuilder.clone(localPaintContext.canvas().getPaint())
			.setColor(ScrollbarStyles.SCROLLBAR_COLOR)
			.build();
		Canvas2D canvas = localPaintContext.canvas()
			.withPaint(paint);

		Rectangle firstEllipseLocation = createDisplayRect(
			translatedScrollPosition.x(),
			translatedScrollPosition.y(),
			translatedScrollSize.width(),
			ScrollbarStyles.SCROLLBAR_INLINE_SIZE,
			positionTranslator);
		canvas.drawEllipse(
			firstEllipseLocation.position().x(), firstEllipseLocation.position().y(),
			firstEllipseLocation.size().width(), firstEllipseLocation.size().height());

		Rectangle mainRectangleLocation = createDisplayRect(
			translatedScrollPosition.x(),
			translatedScrollPosition.y() + ScrollbarStyles.SCROLLBAR_INLINE_SIZE / 2,
			translatedScrollSize.width(),
			translatedScrollSize.height() - ScrollbarStyles.SCROLLBAR_INLINE_SIZE,
			positionTranslator);
		canvas.drawRect(
			mainRectangleLocation.position().x(), mainRectangleLocation.position().y(),
			mainRectangleLocation.size().width(), mainRectangleLocation.size().height());

		Rectangle secondEllipseLocation = createDisplayRect(
			translatedScrollPosition.x(),
			translatedScrollPosition.y() + scrollbarLocation.size().height() - ScrollbarStyles.SCROLLBAR_INLINE_SIZE,
			translatedScrollSize.width(),
			ScrollbarStyles.SCROLLBAR_INLINE_SIZE,
			positionTranslator);
		canvas.drawEllipse(
			secondEllipseLocation.position().x(), secondEllipseLocation.position().y(),
			secondEllipseLocation.size().width(), secondEllipseLocation.size().height());
	}

	private static Rectangle createDisplayRect(
		float inlineStart, float blockStart, float inlineSize, float blockSize, ScrollbarDimensionTranslator positionTranslator
	) {
		return new Rectangle(
			positionTranslator.translateFromUpright(new AbsolutePosition(inlineStart, blockStart)),
			positionTranslator.translateFromUpright(new AbsoluteSize(inlineSize, blockSize)));
	}
	

}
