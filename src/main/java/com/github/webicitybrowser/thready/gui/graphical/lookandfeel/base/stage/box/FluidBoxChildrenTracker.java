package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoundBoxChildrenTracker;

public class FluidBoxChildrenTracker implements BoundBoxChildrenTracker {

	private final List<BoundBox<?, ?>> children = new ArrayList<>(1);
	
	@Override
	public void addChild(BoundBox<?, ?> child) {
		children.add(child);
	}

	@Override
	public List<BoundBox<?, ?>> getAdjustedBoxTree(BoundBox<?, ?> self) {
		return List.of(self);
	}

	@Override
	public List<BoundBox<?, ?>> getChildren() {
		return List.copyOf(children);
	}

}
