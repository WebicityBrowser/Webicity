package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.render.layout.flow.inline;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;

public interface CursorTracker {
	
	void add(AbsoluteSize minimumSize);

	AbsoluteSize getSizeCovered();

	AbsoluteSize sizeCoveredAfterAdd(AbsoluteSize sizeAfterAppend);

	AbsolutePosition getNextPosition();

}
