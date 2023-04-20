package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.render.layout.flow.inline;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;

public class HorizontalCursorTracker implements CursorTracker {

	private AbsoluteSize currentSize = new AbsoluteSize(0, 0);
	
	@Override
	public void add(AbsoluteSize unitSize) {
		this.currentSize = sizeCoveredAfterAdd(unitSize);
	}
	
	@Override
	public AbsoluteSize getSizeCovered() {
		return this.currentSize;
	}

	@Override
	public AbsoluteSize sizeCoveredAfterAdd(AbsoluteSize sizeAfterAppend) {
		float widthComponent = sizeAfterAppend.width() + currentSize.width();
		float heightComponent = Math.max(sizeAfterAppend.height(), currentSize.height());
		return new AbsoluteSize(widthComponent, heightComponent);
	}

	@Override
	public AbsolutePosition getNextPosition() {
		return new AbsolutePosition(currentSize.width(), 0);
	}

}
