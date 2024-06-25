package com.github.webicitybrowser.threadyweb.graphical.layout.flow.cursor;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;

public interface CursorTracker {
	
	void add(AbsoluteSize unitSize);
	
	boolean addWillOverflowLine(AbsoluteSize unitSize, LineDimension lineSize);

	void nextLine();
	
	AbsoluteSize getSizeCovered();

	LineDimension getNextPosition();

}
