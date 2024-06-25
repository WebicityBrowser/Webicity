package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.block;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.size.MaxWidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.size.MinWidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.size.SizeCalculationDirective;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutSizeUtils;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutSizeUtils.LayoutSizingContext;

public final class FlowBlockSizeCalculations {
	
	private FlowBlockSizeCalculations() {}

	public static AbsoluteSize clipContentSize(DirectivePool childStyles, AbsoluteSize contentSize, FlowBlockPrerenderSizingInfo sizingInfo) {
		float[] paddings = sizingInfo.sizingContext().boxOffsetDimensions().totalPadding();
		LayoutSizingContext layoutSizingContext = sizingInfo.sizingContext();

		AbsoluteSize outerSize = LayoutSizeUtils.addPadding(contentSize, paddings);
		AbsoluteSize clippedOuterSize = new AbsoluteSize(
			clipSize(childStyles, outerSize.width(), layoutSizingContext, MinWidthDirective.class, MaxWidthDirective.class),
			clipSize(childStyles, outerSize.height(), layoutSizingContext, MinWidthDirective.class, MaxWidthDirective.class)
		);
		AbsoluteSize clippedContentSize = LayoutSizeUtils.subtractPadding(clippedOuterSize, paddings);

		return clippedContentSize;
	}
	
	public static float clipSize(
		DirectivePool childStyles, float size, LayoutSizingContext layoutSizingContext,
		Class<? extends SizeCalculationDirective> minDirectiveType,
		Class<? extends SizeCalculationDirective> maxDirectiveType) {
		float minWidth = LayoutSizeUtils.computeSize(childStyles, minDirectiveType, layoutSizingContext);
		float maxWidth = LayoutSizeUtils.computeSize(childStyles, maxDirectiveType, layoutSizingContext);

		if (maxWidth != RelativeDimension.UNBOUNDED) {
			size = Math.min(size, maxWidth);
		}
		if (minWidth != RelativeDimension.UNBOUNDED) {
			size = Math.max(size, minWidth);
		}

		return size;
	}

}
