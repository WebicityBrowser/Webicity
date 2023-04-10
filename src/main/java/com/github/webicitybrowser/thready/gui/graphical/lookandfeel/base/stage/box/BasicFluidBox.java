package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box;

import java.util.function.BiFunction;

import com.github.webicitybrowser.thready.gui.directive.core.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.FluidBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.FluidRenderer;

public class BasicFluidBox implements FluidBox {

	private final DirectivePool directives;
	private final BiFunction<Box, Box[], FluidRenderer> rendererGenerator;
	
	public BasicFluidBox(DirectivePool directives, BiFunction<Box, Box[], FluidRenderer> rendererGenerator) {
		this.directives = directives;
		this.rendererGenerator = rendererGenerator;
	}
	
	@Override
	public void addChild(Box child) {
		// TODO Auto-generated method stub

	}

	@Override
	public Box[] getAdjustedBoxTree() {
		return new Box[] { this };
	}

	@Override
	public DirectivePool getStyleDirectives() {
		return this.directives;
	}

	@Override
	public FluidRenderer createRenderer() {
		return rendererGenerator.apply(this, new Box[0]);
	}

}
