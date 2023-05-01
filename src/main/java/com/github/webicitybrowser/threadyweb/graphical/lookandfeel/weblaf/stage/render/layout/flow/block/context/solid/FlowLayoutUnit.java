package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.layout.flow.block.context.solid;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.Painter;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;

public class FlowLayoutUnit implements Unit {

	private final FlowLayoutResult[] childrenResults;
	private final AbsoluteSize minimumSize;

	public FlowLayoutUnit(FlowLayoutResult[] childrenResults, AbsoluteSize minimumSize) {
		this.childrenResults = childrenResults;
		this.minimumSize = minimumSize;
	}
	
	@Override
	public AbsoluteSize getMinimumSize() {
		return this.minimumSize;
	}

	@Override
	public Painter getPainter(Rectangle documentRect) {
		return new FlowLayoutPainter(documentRect, childrenResults);
	}

}
