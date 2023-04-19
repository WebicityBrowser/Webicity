package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.render.layout.flow.block.context.inline;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;

public interface FluidLines {

	void addBox(Box child);

	AbsoluteSize computeTotalSize();

	FluidChildrenResult[] getRenderResults();
	
}
