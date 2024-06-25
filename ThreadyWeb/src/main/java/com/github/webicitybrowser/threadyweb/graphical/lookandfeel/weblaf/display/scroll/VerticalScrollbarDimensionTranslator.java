package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display.scroll;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;

public class VerticalScrollbarDimensionTranslator implements ScrollbarDimensionTranslator {

	@Override
	public AbsolutePosition translateToUpright(AbsolutePosition initialPosition) {
		return initialPosition;
	}

	@Override
	public AbsolutePosition translateFromUpright(AbsolutePosition uprightPosition) {
		return uprightPosition;
	}

	@Override
	public AbsoluteSize translateToUpright(AbsoluteSize initialSize) {
		return initialSize;
	}

	@Override
	public AbsoluteSize translateFromUpright(AbsoluteSize uprightSize) {
		return uprightSize;
	}


}
