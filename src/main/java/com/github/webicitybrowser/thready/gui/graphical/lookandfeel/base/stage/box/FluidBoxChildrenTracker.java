package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoundBoxChildrenTracker;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;

public class FluidBoxChildrenTracker implements BoundBoxChildrenTracker {

	private final List<Box> children = new ArrayList<>(1);

	private final Box parentBox;

	public FluidBoxChildrenTracker(Box parentBox) {
		this.parentBox = parentBox;
	}
	
	@Override
	public void addChild(Box child) {
		children.add(child);
	}

	@Override
	public List<Box> getAdjustedBoxTree() {
		return List.of(parentBox);
	}

	@Override
	public List<Box> getChildren() {
		return List.copyOf(children);
	}

}
