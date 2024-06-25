package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.styled;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.GlobalCompositeContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.LocalCompositeContext;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.util.BoxOffsetDimensions;

public class StyledUnitCompositor {

	public static void composite(StyledUnit unit, GlobalCompositeContext compositeContext, LocalCompositeContext localCompositeContext) {
		compositeContext.addPaintUnit(unit, localCompositeContext);
		compositeInnerUnit(unit, compositeContext, localCompositeContext);
	}

	private static void compositeInnerUnit(StyledUnit unit, GlobalCompositeContext globalPaintContext, LocalCompositeContext localCompositeContext) {
		AbsolutePosition position = localCompositeContext.documentRect().position();
		BoxOffsetDimensions boxOffsetDimensions = unit.context().boxOffsetDimensions();
		float[] padding = boxOffsetDimensions.padding();
		float[] borders = boxOffsetDimensions.borders();
		AbsolutePosition innerPosition = new AbsolutePosition(
			position.x() + borders[0] + padding[0],
			position.y() + borders[2] + padding[2]);
		Rectangle innerDocumentRect = new Rectangle(innerPosition, unit.context().innerUnitSize());

		LocalCompositeContext childCompositeContext = new LocalCompositeContext(innerDocumentRect);

		UIPipeline.composite(unit.context().innerUnit(), globalPaintContext, childCompositeContext);
	}

}
