package com.github.webicitybrowser.thready.gui.graphical.layout.base.flowing.imp;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.Painter;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;

public class FlowingLayoutUnit implements Unit {

	private final FlowingLayoutResult[] childrenResults;
	private final AbsoluteSize minimumSize;

	public FlowingLayoutUnit(FlowingLayoutResult[] childrenResults, AbsoluteSize minimumSize) {
		this.childrenResults = childrenResults;
		this.minimumSize = minimumSize;
	}
	
	@Override
	public AbsoluteSize getMinimumSize() {
		return this.minimumSize;
	}

	@Override
	public Painter getPainter(Rectangle documentRect) {
		return new FlowingLayoutPainter(documentRect, childrenResults);
	}

}
