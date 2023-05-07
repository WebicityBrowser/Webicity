package com.github.webicitybrowser.webicitybrowser.gui.ui.button;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.BasicBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.stage.style.SimpleStylePoolGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.util.SimpleBoxGenerator;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.webicitybrowser.component.CircularButtonComponent;

public class CircularButtonComponentUI implements ComponentUI {

	private final CircularButtonComponent component;
	private final ComponentUI parent;
	private final SimpleStylePoolGenerator stylePoolGenerator;

	public CircularButtonComponentUI(Component component, ComponentUI parent) {
		this.component = (CircularButtonComponent) component;
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
		
		return SimpleBoxGenerator.generateBoxes(() -> new Box[] {
			new BasicBox(
				component, directives,
				(box, children) -> new CircularButtonComponentRenderer(box))
		});
	}

}