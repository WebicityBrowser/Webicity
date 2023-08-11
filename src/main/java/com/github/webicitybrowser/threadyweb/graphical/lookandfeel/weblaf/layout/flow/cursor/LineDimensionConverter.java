package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;

public interface LineDimensionConverter {

	AbsoluteSize getAbsoluteSize(LineDimension lineDimension);

	AbsolutePosition getAbsolutePosition(LineDimension currentPointer);
	
	LineDimension getLineDimension(AbsoluteSize absoluteSize);
	
	LineDimension getLineDimension(AbsolutePosition absolutePosition);
	
}
