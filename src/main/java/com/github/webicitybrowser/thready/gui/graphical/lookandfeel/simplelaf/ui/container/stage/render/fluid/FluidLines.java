package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container.stage.render.fluid;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.FluidBox;

public interface FluidLines {

	void addBox(FluidBox child);

	AbsoluteSize computeTotalSize();

	FluidChildrenResult[] getRenderResults();
	
}
