package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.render.layout.flow.cursor;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;

public class HorizontalLineDimensionConverter implements LineDimensionConverter {

	@Override
	public AbsoluteSize getAbsoluteSize(LineDimension lineDimension) {
		return new AbsoluteSize(lineDimension.run(), lineDimension.depth());
	}

	@Override
	public AbsolutePosition getAbsolutePosition(LineDimension currentPointer) {
		return new AbsolutePosition(currentPointer.run(), currentPointer.depth());
	}

	@Override
	public LineDimension getLineDimension(AbsoluteSize absoluteSize) {
		return new LineDimension(absoluteSize.width(), absoluteSize.height());
	}

	@Override
	public LineDimension getLineDimension(AbsolutePosition absolutePosition) {
		return new LineDimension(absolutePosition.x(), absolutePosition.y());
	}

}
