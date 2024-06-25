package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display.scroll;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;

public interface ScrollbarDimensionTranslator {
	
	AbsolutePosition translateToUpright(AbsolutePosition initialPosition);

	AbsolutePosition translateFromUpright(AbsolutePosition uprightPosition);

	AbsoluteSize translateToUpright(AbsoluteSize initialSize);

	AbsoluteSize translateFromUpright(AbsoluteSize uprightSize);

}
