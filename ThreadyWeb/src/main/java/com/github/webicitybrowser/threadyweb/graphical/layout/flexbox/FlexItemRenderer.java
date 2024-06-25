package com.github.webicitybrowser.threadyweb.graphical.layout.flexbox;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexDirectionDirective.FlexDirection;
import com.github.webicitybrowser.threadyweb.graphical.layout.flexbox.item.FlexItem;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.util.BoxOffsetDimensions;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutSizeUtils;

public final class FlexItemRenderer {
	
	private FlexItemRenderer() {}

	public static AbsoluteSize render(FlexItem flexItem, FlexItemRenderContext flexItemRenderContext) {
		AbsoluteSize adjustedPreferredSize = getAdjustedPreferredSize(flexItem, flexItemRenderContext);
		LocalRenderContext itemLocalRenderContext = LocalRenderContext.create(adjustedPreferredSize, new ContextSwitch[0]);

		RenderedUnit unit = UIPipeline.render(flexItem.getBox(), flexItemRenderContext.globalRenderContext(), itemLocalRenderContext);
		flexItem.setRenderedUnit(unit);

		return adjustFitSize(flexItem, adjustedPreferredSize, unit.fitSize());
	}

	private static AbsoluteSize getAdjustedPreferredSize(FlexItem flexItem, FlexItemRenderContext flexItemRenderContext) {
		AbsoluteSize adjustedPreferredSize = new FlexDimension(
			flexItem.getMainSize(), flexItem.getCrossSize(), flexItemRenderContext.flexDirection()
		).toAbsoluteSize();
		BoxOffsetDimensions boxOffsetDimensions = flexItem.getSizePreferences().getBoxOffsetDimensions();
		float[] zeroAutoMargins = FlexMarginCalculations.zeroAutoMargins(boxOffsetDimensions.margins());
		adjustedPreferredSize = LayoutSizeUtils.subtractPadding(adjustedPreferredSize, zeroAutoMargins);
		adjustedPreferredSize = LayoutSizeUtils.subtractPadding(adjustedPreferredSize, boxOffsetDimensions.padding());
		adjustedPreferredSize = LayoutSizeUtils.subtractPadding(adjustedPreferredSize, boxOffsetDimensions.borders());
		return adjustedPreferredSize;
	}

	private static AbsoluteSize adjustFitSize(FlexItem flexItem, AbsoluteSize adjustedPreferredSize, AbsoluteSize fitSize) {
		BoxOffsetDimensions boxOffsetDimensions = flexItem.getSizePreferences().getBoxOffsetDimensions();
		fitSize = new AbsoluteSize(
			adjustedPreferredSize.width() != RelativeDimension.UNBOUNDED ? adjustedPreferredSize.width() : fitSize.width(),
			adjustedPreferredSize.height() != RelativeDimension.UNBOUNDED ? adjustedPreferredSize.height() : fitSize.height());
		float[] zeroAutoMargins = FlexMarginCalculations.zeroAutoMargins(boxOffsetDimensions.margins());
		fitSize = LayoutSizeUtils.addPadding(fitSize, zeroAutoMargins);
		fitSize = LayoutSizeUtils.addPadding(fitSize, boxOffsetDimensions.padding());
		fitSize = LayoutSizeUtils.addPadding(fitSize, boxOffsetDimensions.borders());
		return fitSize;
	}

	public static record FlexItemRenderContext(GlobalRenderContext globalRenderContext, FlexDirection flexDirection) {}

}
