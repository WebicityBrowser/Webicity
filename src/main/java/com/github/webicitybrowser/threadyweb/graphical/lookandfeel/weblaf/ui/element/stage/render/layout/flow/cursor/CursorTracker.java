package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.render.layout.flow.cursor;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;

public interface CursorTracker {
	
	void add(AbsoluteSize minimumSize);
	
	boolean addWillOverflowLine(AbsoluteSize unitSize, AbsoluteSize bounds);
	
	void nextLine();

	AbsoluteSize getSizeCovered();

	AbsoluteSize sizeCoveredAfterAdd(AbsoluteSize sizeAfterAppend);

	AbsolutePosition getNextPosition();

	AbsoluteSize getFullLineSize(AbsoluteSize bounds);

}
