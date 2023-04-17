package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import com.github.webicitybrowser.thready.gui.directive.core.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.FluidBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.SolidBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.SolidRenderer;

public class BasicSolidBox implements SolidBox {
	
	private final DirectivePool directives;
	private final BiFunction<Box, Box[], SolidRenderer> rendererGenerator;
	
	private final List<Box> children = new ArrayList<>();
	
	private boolean isSolidBoxContext;
	private Box currentFluidGroup;

	public BasicSolidBox(DirectivePool directives, BiFunction<Box, Box[], SolidRenderer> rendererGenerator) {
		this.directives = directives;
		this.rendererGenerator = rendererGenerator;
	}
	
	@Override
	public void addChild(Box child) {
		if (child instanceof SolidBox) {
			addSolidBox(child);
		} else if (child instanceof FluidBox) {
			addFluidBox(child);
		} else {
			throw new UnsupportedOperationException("Unsupported box type");
		}
	}

	@Override
	public Box[] getAdjustedBoxTree() {
		return new Box[] { this };
	}

	@Override
	public SolidRenderer createRenderer() {
		return rendererGenerator.apply(this, children.toArray(Box[]::new));
	}

	@Override
	public DirectivePool getStyleDirectives() {
		return this.directives;
	}
	
	private void addSolidBox(Box child) {
		if (!isSolidBoxContext) {
			startSolidBoxContext();
		}
		children.add(child);
		currentFluidGroup = null;
	}
	
	private void addFluidBox(Box child) {
		if (!isSolidBoxContext) {
			children.add(child);
			return;
		}
		startFluidGroup();
		currentFluidGroup.addChild(child);
	}

	private void startSolidBoxContext() {
		isSolidBoxContext = true;
		if (children.isEmpty()) {
			return;
		}
		List<Box> priorChildren = List.copyOf(children);
		children.clear();
		startFluidGroup();
		for (Box priorChild: priorChildren) {
			currentFluidGroup.addChild(priorChild);
		}
	}

	private void startFluidGroup() {
		if (currentFluidGroup != null) {
			return;
		}
		currentFluidGroup = new BasicSolidBox(directives, rendererGenerator);
		children.add(currentFluidGroup);
		// TODO: Is the passed-through renderer generator fine?
	}
	
}
