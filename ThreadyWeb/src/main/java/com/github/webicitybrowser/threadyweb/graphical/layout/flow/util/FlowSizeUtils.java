package com.github.webicitybrowser.threadyweb.graphical.layout.flow.util;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;

public final class FlowSizeUtils {
	
	private FlowSizeUtils() {}

	public static AbsoluteSize enforcePreferredSize(AbsoluteSize rawChildSize, AbsoluteSize enforcedSize) {
		float widthComponent = enforcedSize.width() != RelativeDimension.UNBOUNDED ?
			enforcedSize.width() :
			rawChildSize.width();

		float heightComponent = enforcedSize.height() != RelativeDimension.UNBOUNDED ?
			enforcedSize.height() :
			rawChildSize.height();

		return new AbsoluteSize(widthComponent, heightComponent);
	}

}
