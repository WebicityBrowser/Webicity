package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.FluidBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.Renderer;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public class BasicBox implements Box {
	
	private final Component component;
	private final DirectivePool directives;
	private final BiFunction<Box, Box[], Renderer> rendererGenerator;
	
	private final List<Box> children = new ArrayList<>();
	
	private boolean isSolidBoxContext;
	private Box currentFluidGroup;

	public BasicBox(Component component, DirectivePool directives, BiFunction<Box, Box[], Renderer> rendererGenerator) {
		this.component = component;
		this.directives = directives;
		this.rendererGenerator = rendererGenerator;
	}
	
	@Override
	public Component getOwningComponent() {
		return component;
	}
	
	@Override
	public void addChild(Box child) {
		if (child instanceof FluidBox) {
			addFluidBox(child);
		} else {
			addSolidBox(child);
		}
	}

	@Override
	public Box[] getAdjustedBoxTree() {
		return new Box[] { this };
	}

	@Override
	public Renderer createRenderer() {
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
		currentFluidGroup = new BasicBox(component, directives, rendererGenerator);
		children.add(currentFluidGroup);
		// TODO: Is the passed-through renderer generator fine?
	}
	
}
