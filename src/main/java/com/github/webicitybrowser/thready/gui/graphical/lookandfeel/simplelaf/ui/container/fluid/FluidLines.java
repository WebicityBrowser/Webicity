package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container.fluid;

import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundBox;

public interface FluidLines {

	void addBox(BoundBox<?, ?> child);

	LayoutResult getLayoutResult();
	
}
