package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxChildrenTracker;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;

public class FluidBoxChildrenTracker implements BoxChildrenTracker {

	private final List<Box> children = new ArrayList<>(1);

	private final Box parentBox;

	public FluidBoxChildrenTracker(Box parentBox) {
		this.parentBox = parentBox;
	}
	
	@Override
	public void addChild(Box child) {
		for (Box adjustedChild: child.getAdjustedBoxTree()) {
			children.add(adjustedChild);
		}
	}

	@Override
	public List<Box> getChildren() {
		return List.copyOf(children);
	}

	@Override
	public List<Box> getAdjustedBoxTree() {
		return List.of(parentBox);
	}

}
