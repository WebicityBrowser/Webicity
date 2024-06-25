package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.block;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutSizeUtils.LayoutSizingContext;

public record FlowBlockPrerenderSizingInfo(
	AbsoluteSize forcedChildContentSize, AbsoluteSize preferredChildContentSize, AbsoluteSize parentSize, LayoutSizingContext sizingContext
) {}
