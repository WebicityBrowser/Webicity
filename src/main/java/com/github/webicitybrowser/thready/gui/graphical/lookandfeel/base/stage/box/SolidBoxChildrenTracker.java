package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoundBoxChildrenTracker;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;

public class SolidBoxChildrenTracker implements BoundBoxChildrenTracker {
	
	private final List<Box> children = new ArrayList<>(1);
	
	private final Box parentBox;
	private final UIDisplay<?, ChildrenBox, ?> anonDisplay;
	
	private boolean isSolidBoxContext;
	private BoundBoxChildrenTracker currentFluidChildrenTracker;
	
	public SolidBoxChildrenTracker(Box parentBox, UIDisplay<?, ChildrenBox, ?> anonDisplay) {
		this.parentBox = parentBox;
		this.anonDisplay = anonDisplay;
	}
	
	@Override
	public void addChild(Box child) {
		if (child.isFluid()) {
			addFluidBox(child);
		} else {
			addSolidBox(child);
		}
	}

	@Override
	public List<Box> getAdjustedBoxTree() {
		return List.of(parentBox);
	}
	
	@Override
	public List<Box> getChildren() {
		return List.copyOf(children);
	}
	
	private void addSolidBox(Box child) {
		if (!isSolidBoxContext) {
			startSolidBoxContext();
		}
		children.add(child);
		currentFluidChildrenTracker = null;
	}
	
	private void addFluidBox(Box child) {
		if (!isSolidBoxContext) {
			children.add(child);
			return;
		}
		
		startFluidGroup(false);
		currentFluidChildrenTracker.addChild(child);
	}

	private void startSolidBoxContext() {
		isSolidBoxContext = true;
		if (children.isEmpty()) {
			return;
		}
		

		List<Box> priorChildren = List.copyOf(children);
		startFluidGroup(true);
		for (Box priorChild: priorChildren) {
			currentFluidChildrenTracker.addChild(priorChild);
		}
	}

	private void startFluidGroup(boolean clearPrior) {
		if (currentFluidChildrenTracker != null) {
			return;
		}
		
		ChildrenBox anonymousBox = new BasicAnonymousFluidBox(anonDisplay, parentBox.owningComponent(), parentBox.styleDirectives());
		// TODO: Is the passed-through renderer generator fine?
		if (clearPrior) {
			children.clear();
		}
		
		children.add(anonymousBox);
		this.currentFluidChildrenTracker = anonymousBox.getChildrenTracker();
	}

}
