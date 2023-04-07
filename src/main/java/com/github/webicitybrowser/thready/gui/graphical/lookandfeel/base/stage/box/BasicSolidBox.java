package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box;

import java.util.function.Function;

import com.github.webicitybrowser.thready.gui.directive.core.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.SolidBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.SolidRenderer;

public class BasicSolidBox implements SolidBox {
	
	private final DirectivePool directives;
	private final Function<Box, SolidRenderer> rendererGenerator;

	public BasicSolidBox(DirectivePool directives, Function<Box,  SolidRenderer> rendererGenerator) {
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
	public SolidRenderer createRenderer() {
		return rendererGenerator.apply(this);
	}

	@Override
	public DirectivePool getStyleDirectives() {
		return this.directives;
	}

}
