package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.styled;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.paint.BackgroundPainter;

public final class StyledUnitPainter {

	private StyledUnitPainter() {}

	public static void paint(StyledUnit unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		BackgroundPainter.paintBackground(
			unit.context().styleDirectives(),
			globalPaintContext, localPaintContext);
		paintInnerUnit(unit, globalPaintContext, localPaintContext);
	}

	private static void paintInnerUnit(StyledUnit unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		AbsolutePosition position = localPaintContext.documentRect().position();
		AbsolutePosition innerPosition = new AbsolutePosition(
			position.x() + unit.context().padding()[0],
			position.y() + unit.context().padding()[2]);
		Rectangle innerDocumentRect = new Rectangle(innerPosition, unit.context().innerUnitSize());

		LocalPaintContext innerLocalPaintContext = new LocalPaintContext(
			localPaintContext.canvas(),
			innerDocumentRect,
			localPaintContext.documentRect());

		UIPipeline.paint(unit.context().innerUnit(), globalPaintContext, innerLocalPaintContext);
	}

}
