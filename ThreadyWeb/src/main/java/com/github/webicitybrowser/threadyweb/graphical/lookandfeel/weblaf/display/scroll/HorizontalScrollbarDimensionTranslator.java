package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display.scroll;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;

public class HorizontalScrollbarDimensionTranslator implements ScrollbarDimensionTranslator {

	@Override
	public AbsolutePosition translateToUpright(AbsolutePosition initialPosition) {
		return new AbsolutePosition(initialPosition.y(), initialPosition.x());
	}

	@Override
	public AbsolutePosition translateFromUpright(AbsolutePosition uprightPosition) {
		return new AbsolutePosition(uprightPosition.y(), uprightPosition.x());
	}

	@Override
	public AbsoluteSize translateToUpright(AbsoluteSize initialSize) {
		return new AbsoluteSize(initialSize.height(), initialSize.width());
	}

	@Override
	public AbsoluteSize translateFromUpright(AbsoluteSize uprightSize) {
		return new AbsoluteSize(uprightSize.height(), uprightSize.width());
	}
	
}
