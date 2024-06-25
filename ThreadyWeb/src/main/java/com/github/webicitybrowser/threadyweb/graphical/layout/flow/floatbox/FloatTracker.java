package com.github.webicitybrowser.threadyweb.graphical.layout.flow.floatbox;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.floatbox.imp.FloatTrackerImp;

public interface FloatTracker {
	
	void addLeftFloat(Rectangle rect);

	void addRightFloat(Rectangle rect);

	float getClearedLeftBlockPosition(float blockStart);

	float getClearedRightBlockPosition(float blockStart);

	float getLeftInlineOffset(float blockStart);

	float getRightInlineOffset(float blockStart, float inlineEnd);

	float getFitBlockPosition(float blockStart, float inlineEnd, AbsoluteSize itemSize);

	static FloatTracker create() {
		return new FloatTrackerImp();
	}

}
