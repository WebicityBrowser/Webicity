package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flexbox;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.text.FontMetrics;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexDirectionDirective.FlexDirection;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flexbox.item.FlexItem;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.util.LayoutSizeUtils;

public final class FlexItemRenderer {
	
	private FlexItemRenderer() {}

	public static AbsoluteSize render(FlexItem flexItem, FlexItemRenderContext flexItemRenderContext) {
		AbsoluteSize adjustedPreferredSize = getAdjustedPreferredSize(flexItem, flexItemRenderContext);
		
		LocalRenderContext itemLocalRenderContext = LocalRenderContext.create(
			adjustedPreferredSize, flexItemRenderContext.parentFontMetrics(), new ContextSwitch[0]
		);

		RenderedUnit unit = UIPipeline.render(flexItem.getBox(), flexItemRenderContext.globalRenderContext(), itemLocalRenderContext);
		flexItem.setRenderedUnit(unit);

		return adjustFitSize(flexItem, unit.fitSize());
	}

	private static AbsoluteSize getAdjustedPreferredSize(FlexItem flexItem, FlexItemRenderContext flexItemRenderContext) {
		AbsoluteSize adjustedPreferredSize = new FlexDimension(
			flexItem.getMainSize(), flexItem.getCrossSize(), flexItemRenderContext.flexDirection()
		).toAbsoluteSize();
		float[] zeroAutoMargins = FlexMarginCalculations.zeroAutoMargins(flexItem.getSizePreferences().getMargins());
		adjustedPreferredSize = LayoutSizeUtils.subtractPadding(adjustedPreferredSize, zeroAutoMargins);
		adjustedPreferredSize = LayoutSizeUtils.subtractPadding(adjustedPreferredSize, flexItem.getSizePreferences().getPadding());
		return adjustedPreferredSize;
	}

	private static AbsoluteSize adjustFitSize(FlexItem flexItem, AbsoluteSize fitSize) {
		float[] zeroAutoMargins = FlexMarginCalculations.zeroAutoMargins(flexItem.getSizePreferences().getMargins());
		fitSize = LayoutSizeUtils.addPadding(fitSize, zeroAutoMargins);
		fitSize = LayoutSizeUtils.addPadding(fitSize, flexItem.getSizePreferences().getPadding());
		return fitSize;
	}

	public static record FlexItemRenderContext(
		GlobalRenderContext globalRenderContext, FlexDirection flexDirection, FontMetrics parentFontMetrics
	) {}

}
