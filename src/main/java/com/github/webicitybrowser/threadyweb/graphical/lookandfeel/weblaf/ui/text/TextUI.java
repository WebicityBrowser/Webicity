package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text;

import com.github.webicitybrowser.thready.gui.directive.core.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.BasicFluidBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.stage.render.TextComponentRenderer;
import com.github.webicitybrowser.threadyweb.tree.TextComponent;

public class TextUI implements ComponentUI {
	
	private final TextComponent component;
	

	public TextUI(Component component, ComponentUI parent) {
		this.component = (TextComponent) component;
	}

	@Override
	public TextComponent getComponent() {
		return this.component;
	}

	@Override
	public Box[] generateBoxes(BoxContext context, DirectivePool parentDirectives, StyleGenerator styleGenerator) {
		// I don't think text can have it's own styling
		return new Box[] { createBox(parentDirectives) };
	}

	private Box createBox(DirectivePool parentDirectives) {
		return new BasicFluidBox(
			parentDirectives,
			(box, children) -> new TextComponentRenderer(component, box));
	}

}
