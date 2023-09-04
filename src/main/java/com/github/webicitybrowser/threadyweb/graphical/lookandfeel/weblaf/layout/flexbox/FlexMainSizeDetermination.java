package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flexbox;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexDirectionDirective.FlexDirection;

public final class FlexMainSizeDetermination {

	private FlexMainSizeDetermination() {}

	public static void determineMainSizes(FlexLine flexLine, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		FlexDirection flexDirection = flexLine.getFlexDirection();
		for (FlexItem flexItem: flexLine.getFlexItems()) {
			Box box = flexItem.getBox();
			// TODO: Check flex basis
			AbsoluteSize parentSize = new AbsoluteSize(RelativeDimension.UNBOUNDED, RelativeDimension.UNBOUNDED);
			// TODO: Properly determine preferred size
			LocalRenderContext childLocalRenderContext = FlexUtils.createChildRenderContext(flexItem, parentSize, localRenderContext);
			RenderedUnit renderedUnit = UIPipeline.render(box, globalRenderContext, childLocalRenderContext);
			AbsoluteSize fitSize = renderedUnit.fitSize();
			FlexDimension flexDimension = FlexDimension.createFrom(fitSize, flexDirection);
			flexItem.setBaseSize(flexDimension.main());
			flexItem.setHypotheticalMainSize(flexDimension.main());
		}
	}

}
