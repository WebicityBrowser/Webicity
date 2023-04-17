package com.github.webicitybrowser.thready.gui.graphical.layout.base.flowing.imp;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;

public class RenderCursorTracker {
	
	private float nextYPos = 0;
	private float highestWidth = 0;

	public float getNextYPos() {
		return this.nextYPos;
	}
	
	public void increaseNextYPos(float amount) {
		this.nextYPos += amount;
	}

	public void recordWidth(float width) {
		highestWidth = Math.max(highestWidth, width);
	}
	
	public AbsoluteSize getCoveredSize() {
		return new AbsoluteSize(highestWidth, nextYPos);
	}
	
}
