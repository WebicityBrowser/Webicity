package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.text;

import com.github.webicitybrowser.thready.gui.directive.core.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.BasicFluidBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.stage.style.SimpleStylePoolGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.text.stage.render.TextComponentRenderer;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.util.SimpleBoxGenerator;
import com.github.webicitybrowser.thready.gui.tree.basics.TextComponent;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public class TextUI implements ComponentUI {
	
	private Component component;
	
	private final SimpleStylePoolGenerator stylePoolGenerator;

	public TextUI(Component component, ComponentUI parent) {
		this.component = component;
		this.stylePoolGenerator = new SimpleStylePoolGenerator(component.getStyleDirectives());
	}

	@Override
	public Component getComponent() {
		return this.component;
	}

	@Override
	public Box[] generateBoxes(BoxContext context, DirectivePool parentDirectives, StyleGenerator styleGenerator) {
		DirectivePool directives = stylePoolGenerator.createStylePool(parentDirectives, styleGenerator);
		return SimpleBoxGenerator.generateBoxes(() -> new Box[] { createBox(directives) });
	}

	private Box createBox(DirectivePool directives) {
		return new BasicFluidBox(
			directives,
			(box, children) -> new TextComponentRenderer((TextComponent) component, box));
	}

}
