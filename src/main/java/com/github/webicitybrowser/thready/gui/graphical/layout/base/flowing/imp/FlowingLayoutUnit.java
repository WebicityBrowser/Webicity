package com.github.webicitybrowser.thready.gui.graphical.layout.base.flowing.imp;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.Painter;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;

public class FlowingLayoutUnit implements Unit {

	private final FlowingLayoutResult[] childrenResults;

	public FlowingLayoutUnit(FlowingLayoutResult[] childrenResults) {
		this.childrenResults = childrenResults;
	}

	@Override
	public Painter getPainter(Rectangle documentRect) {
		return new FlowingLayoutPainter(documentRect, childrenResults);
	}

}
