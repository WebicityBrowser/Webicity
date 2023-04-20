package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.render.layout.flow.cursor;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;

public class HorizontalCursorTracker implements CursorTracker {

	private AbsoluteSize currentSize = new AbsoluteSize(0, 0);
	private AbsolutePosition currentPointer = new AbsolutePosition(0, 0);
	
	@Override
	public void add(AbsoluteSize unitSize) {
		this.currentSize = sizeCoveredAfterAdd(unitSize);
		float newPointerX = currentPointer.x() + unitSize.width();
		this.currentPointer = new AbsolutePosition(newPointerX, currentPointer.y());
	}

	@Override
	public boolean addWillOverflowLine(AbsoluteSize unitSize, AbsoluteSize bounds) {
		if (bounds.width() == -1) {
			return false;
		}
		return sizeCoveredAfterAdd(unitSize).width() > bounds.width();
	}
	
	@Override
	public AbsoluteSize getSizeCovered() {
		return this.currentSize;
	}

	@Override
	public AbsoluteSize sizeCoveredAfterAdd(AbsoluteSize sizeAfterAppend) {
		float widthComponent = Math.max(sizeAfterAppend.width(), currentPointer.x() + currentSize.width());
		float heightComponent = Math.max(sizeAfterAppend.height(), currentPointer.y() + currentSize.height());
		return new AbsoluteSize(widthComponent, heightComponent);
	}

	@Override
	public AbsolutePosition getNextPosition() {
		return currentPointer;
	}

	@Override
	public void nextLine() {
		currentPointer = new AbsolutePosition(0, currentSize.height());
	}

	@Override
	public AbsoluteSize getFullLineSize(AbsoluteSize bounds) {
		return new AbsoluteSize(bounds.width(), -1);
	}

}
