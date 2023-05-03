package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.BasicFluidBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.stage.render.TextComponentRenderer;
import com.github.webicitybrowser.threadyweb.tree.TextComponent;

public class TextUI implements ComponentUI {
	
	private final TextComponent component;
	private final ComponentUI parent;
	

	public TextUI(Component component, ComponentUI parent) {
		this.component = (TextComponent) component;
		this.parent = parent;
	}

	@Override
	public TextComponent getComponent() {
		return this.component;
	}
	
	@Override
	public void invalidate(InvalidationLevel level) {
		parent.invalidate(level);
	}

	@Override
	public Box[] generateBoxes(BoxContext context, DirectivePool parentDirectives, StyleGenerator styleGenerator) {
		// I don't think text can have it's own styling
		return new Box[] { createBox(parentDirectives) };
	}

	private Box createBox(DirectivePool parentDirectives) {
		return new BasicFluidBox(
			component, parentDirectives,
			(box, children) -> new TextComponentRenderer(component, box));
	}

}
