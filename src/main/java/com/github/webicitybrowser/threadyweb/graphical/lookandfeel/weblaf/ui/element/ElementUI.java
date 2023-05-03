package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.style.WebStylePoolGenerator;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.box.ElementUIBoxGenerator;
import com.github.webicitybrowser.threadyweb.tree.ElementComponent;

public class ElementUI implements ComponentUI {
	
	private final ElementComponent component;
	private final ComponentUI parent;
	private final ElementUIBoxGenerator boxGenerator;
	private final WebStylePoolGenerator stylePoolGenerator;

	public ElementUI(Component component, ComponentUI parent) {
		this.component = (ElementComponent) component;
		this.parent = parent;
		this.boxGenerator = new ElementUIBoxGenerator(this.component, parent);
		this.stylePoolGenerator = new WebStylePoolGenerator(component.getStyleDirectives());
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
		DirectivePool styleDirectives = stylePoolGenerator.createStylePool(parentDirectives, styleGenerator);
		return boxGenerator.generateBoxes(context, styleDirectives, styleGenerator);
	}

}
