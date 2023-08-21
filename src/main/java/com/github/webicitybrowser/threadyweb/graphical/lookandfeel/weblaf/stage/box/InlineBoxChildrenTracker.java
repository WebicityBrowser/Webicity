package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.box;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxChildrenTracker;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.CloneBox;

public class InlineBoxChildrenTracker implements BoxChildrenTracker {

	private final CloneBox parentBox;
	private final List<Box> children = new ArrayList<>();

	private boolean containsSolids = false;

	public InlineBoxChildrenTracker(CloneBox parentBox) {
		this.parentBox = parentBox;
	}

	@Override
	public void addChild(Box child) {
		for (Box adjustedChild: child.getAdjustedBoxTree()) {
			containsSolids = containsSolids || !adjustedChild.isFluid();
			children.add(adjustedChild);
		}
	}

	@Override
	public List<Box> getChildren() {
		return children;
	}

	@Override
	public List<Box> getAdjustedBoxTree() {
		if (containsSolids) {
			return getAdjustedBoxTreeWithSolids();
		}

		return List.of(parentBox);
	}

	private List<Box> getAdjustedBoxTreeWithSolids() {
		int pointer = 0;
		List<Box> adjustedTree = new ArrayList<>();
		while (pointer < children.size()) {
			Box child = children.get(pointer);
			if (child.isFluid()) {
				List<Box> fluidChildren = gatherFluidChildren(pointer);
				CloneBox fluidParent = parentBox.cloneEmpty();
				fluidParent.getChildrenTracker().addAllChildren(fluidChildren);
				adjustedTree.add(fluidParent);
				pointer += fluidChildren.size();
			} else {
				adjustedTree.add(child);
				pointer++;
			}
		}

		return adjustedTree;
	}

	private List<Box> gatherFluidChildren(int pointer) {
		List<Box> fluidChildren = new ArrayList<>();
		while (pointer < children.size()) {
			Box child = children.get(pointer);
			if (child.isFluid()) {
				fluidChildren.add(child);
				pointer++;
			} else {
				break;
			}
		}

		return fluidChildren;
	}
	
}
