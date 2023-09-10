package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.block;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.util.LayoutSizeUtils;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.util.LayoutSizeUtils.LayoutSizingContext;

public final class FlowBlockSizeCalculations {
	
	private FlowBlockSizeCalculations() {}

	public static AbsoluteSize clipContentSize(Box childBox, AbsoluteSize contentSize, LayoutSizingContext layoutSizingContext, float[] paddings) {
		AbsoluteSize outerSize = LayoutSizeUtils.addPadding(contentSize, paddings);
		AbsoluteSize clippedOuterSize = new AbsoluteSize(
			clipWidth(childBox, outerSize.width(), layoutSizingContext),
			clipHeight(childBox, outerSize.height(), layoutSizingContext, false)
		);
		AbsoluteSize clippedContentSize = LayoutSizeUtils.subtractPadding(clippedOuterSize, paddings);

		return clippedContentSize;
	}
	
	public static float clipWidth(Box box, float width, LayoutSizingContext layoutSizingContext) {
		float minWidth = LayoutSizeUtils.computeMinWidth(box.styleDirectives(), layoutSizingContext);
		float maxWidth = LayoutSizeUtils.computeMaxWidth(box.styleDirectives(), layoutSizingContext);

		if (maxWidth != RelativeDimension.UNBOUNDED) {
			width = Math.min(width, maxWidth);
		}
		if (minWidth != RelativeDimension.UNBOUNDED) {
			width = Math.max(width, minWidth);
		}

		return width;
	}

	public static float clipHeight(Box box, float height, LayoutSizingContext layoutSizingContext, boolean minimize) {
		float minHeight = LayoutSizeUtils.computeMinHeight(box.styleDirectives(), layoutSizingContext);
		float maxHeight = LayoutSizeUtils.computeMaxHeight(box.styleDirectives(), layoutSizingContext);

		if (maxHeight != RelativeDimension.UNBOUNDED) {
			height = Math.min(height, maxHeight);
		}
		if (minHeight != RelativeDimension.UNBOUNDED) {
			height = Math.max(height, minHeight);
		}

		return height;
	}

}
