package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import com.github.webicitybrowser.thready.gui.directive.core.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.SolidBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.SolidRenderer;

public class BasicSolidBox implements SolidBox {
	
	private final DirectivePool directives;
	private final BiFunction<Box, Box[], SolidRenderer> rendererGenerator;
	
	private final List<Box> children = new ArrayList<>();

	public BasicSolidBox(DirectivePool directives, BiFunction<Box, Box[], SolidRenderer> rendererGenerator) {
		this.directives = directives;
		this.rendererGenerator = rendererGenerator;
	}
	
	@Override
	public void addChild(Box child) {
		children.add(child);
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

}
