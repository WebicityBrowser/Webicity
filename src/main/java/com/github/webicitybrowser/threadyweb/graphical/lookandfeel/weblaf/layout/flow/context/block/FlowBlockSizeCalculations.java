package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.block;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.util.LayoutSizeUtils;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.util.LayoutSizeUtils.LayoutSizingContext;

public final class FlowBlockSizeCalculations {
	
	private FlowBlockSizeCalculations() {}

	public static AbsoluteSize clipContentSize(DirectivePool childStyles, AbsoluteSize contentSize, FlowBlockPrerenderSizingInfo sizingInfo) {
		float[] paddings = sizingInfo.totalPadding();
		LayoutSizingContext layoutSizingContext = sizingInfo.sizingContext();

		AbsoluteSize outerSize = LayoutSizeUtils.addPadding(contentSize, paddings);
		AbsoluteSize clippedOuterSize = new AbsoluteSize(
			clipWidth(childStyles, outerSize.width(), layoutSizingContext),
			clipHeight(childStyles, outerSize.height(), layoutSizingContext, false)
		);
		AbsoluteSize clippedContentSize = LayoutSizeUtils.subtractPadding(clippedOuterSize, paddings);

		return clippedContentSize;
	}
	
	public static float clipWidth(DirectivePool childStyles, float width, LayoutSizingContext layoutSizingContext) {
		float minWidth = LayoutSizeUtils.computeMinWidth(childStyles, layoutSizingContext);
		float maxWidth = LayoutSizeUtils.computeMaxWidth(childStyles, layoutSizingContext);

		if (maxWidth != RelativeDimension.UNBOUNDED) {
			width = Math.min(width, maxWidth);
		}
		if (minWidth != RelativeDimension.UNBOUNDED) {
			width = Math.max(width, minWidth);
		}

		return width;
	}

	public static float clipHeight(DirectivePool childStyles, float height, LayoutSizingContext layoutSizingContext, boolean minimize) {
		float minHeight = LayoutSizeUtils.computeMinHeight(childStyles, layoutSizingContext);
		float maxHeight = LayoutSizeUtils.computeMaxHeight(childStyles, layoutSizingContext);

		if (maxHeight != RelativeDimension.UNBOUNDED) {
			height = Math.min(height, maxHeight);
		}
		if (minHeight != RelativeDimension.UNBOUNDED) {
			height = Math.max(height, minHeight);
		}

		return height;
	}

}
