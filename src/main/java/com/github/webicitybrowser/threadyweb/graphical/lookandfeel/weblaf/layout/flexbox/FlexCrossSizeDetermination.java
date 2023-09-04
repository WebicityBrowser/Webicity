package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flexbox;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexDirectionDirective.FlexDirection;

public final class FlexCrossSizeDetermination {
	
	private FlexCrossSizeDetermination() {}

	public static void determineCrossSizes(FlexLine flexLine, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		FlexDirection flexDirection = flexLine.getFlexDirection();
		for (FlexItem flexItem: flexLine.getFlexItems()) {
			determineStretchCrossSize(flexItem, flexDirection, globalRenderContext, localRenderContext);
		}

		AbsoluteSize containerSize = localRenderContext.getPreferredSize();
		FlexDimension containerDimensions = FlexDimension.createFrom(containerSize, flexDirection);
		float lineCrossSize = containerDimensions.cross();
		if (lineCrossSize != RelativeDimension.UNBOUNDED) {
			flexLine.setCrossSize(lineCrossSize);
		}

		for (FlexItem flexItem: flexLine.getFlexItems()) {
			flexItem.setCrossSize(flexLine.getCrossSize());
		}
	}

	private static void determineStretchCrossSize(
		FlexItem flexItem, FlexDirection flexDirection, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext
	) {
		determineBaselineCrossSize(flexItem, flexDirection, globalRenderContext, localRenderContext);
	}

	private static void determineBaselineCrossSize(
		FlexItem flexItem, FlexDirection flexDirection, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext
	) {
		Box box = flexItem.getBox();
		FlexDimension preferredSize = new FlexDimension(flexItem.getMainSize(), RelativeDimension.UNBOUNDED, flexDirection);
		LocalRenderContext childLocalRenderContext = FlexUtils.createChildRenderContext(flexItem, preferredSize.toAbsoluteSize(), localRenderContext);
		RenderedUnit renderedUnit = UIPipeline.render(box, globalRenderContext, childLocalRenderContext);
		AbsoluteSize fitSize = renderedUnit.fitSize();
		FlexDimension flexDimension = FlexDimension.createFrom(fitSize, flexDirection);
		flexItem.setCrossSize(flexDimension.cross());
		flexItem.setRenderedUnit(renderedUnit);
	}

}
