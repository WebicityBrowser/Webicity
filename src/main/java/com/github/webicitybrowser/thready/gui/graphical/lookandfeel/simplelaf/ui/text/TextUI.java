package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.text;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
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
	
	private final Component component;
	private final ComponentUI parent;
	
	private final SimpleStylePoolGenerator stylePoolGenerator;


	public TextUI(Component component, ComponentUI parent) {
		this.component = component;
		this.parent = parent;
		this.stylePoolGenerator = new SimpleStylePoolGenerator(this, component.getStyleDirectives());
	}

	@Override
	public Component getComponent() {
		return this.component;
	}

	@Override
	public void invalidate(InvalidationLevel level) {
		parent.invalidate(level);
	}
	
	@Override
	public Box[] generateBoxes(BoxContext context, DirectivePool parentDirectives, StyleGenerator styleGenerator) {
		DirectivePool directives = stylePoolGenerator.createStylePool(parentDirectives, styleGenerator);
		return SimpleBoxGenerator.generateBoxes(() -> new Box[] { createBox(directives) });
	}

	private Box createBox(DirectivePool directives) {
		return new BasicFluidBox(
			component, directives,
			(box, children) -> new TextComponentRenderer((TextComponent) component, box));
	}

}
