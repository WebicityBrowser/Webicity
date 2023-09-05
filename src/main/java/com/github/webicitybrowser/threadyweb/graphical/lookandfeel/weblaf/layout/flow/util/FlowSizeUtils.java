package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.util;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;

public final class FlowSizeUtils {
	
	private FlowSizeUtils() {}

	public static AbsoluteSize enforcePreferredSize(
		AbsoluteSize rawChildSize, AbsoluteSize contentSize, AbsoluteSize preferredSize
	) {
		float widthComponent = preferredSize.width() != RelativeDimension.UNBOUNDED ?
			contentSize.width() :
			rawChildSize.width();

		float heightComponent = preferredSize.height() != RelativeDimension.UNBOUNDED ?
			contentSize.height() :
			rawChildSize.height();

		return new AbsoluteSize(widthComponent, heightComponent);
	}

}
