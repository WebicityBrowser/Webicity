package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element;

import com.github.webicitybrowser.thready.gui.directive.core.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.style.WebStylePoolGenerator;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.box.ElementUIBoxGenerator;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebBoxGenerator;
import com.github.webicitybrowser.threadyweb.tree.ElementComponent;

public class ElementUI implements ComponentUI {
	
	private final ElementComponent component;
	private final ElementUIBoxGenerator boxGenerator;
	private final WebStylePoolGenerator stylePoolGenerator;

	public ElementUI(Component component, ComponentUI parent) {
		this.component = (ElementComponent) component;
		this.boxGenerator = new ElementUIBoxGenerator(this.component, parent);
		this.stylePoolGenerator = new WebStylePoolGenerator(component.getStyleDirectives());
	}
	
	@Override
	public Component getComponent() {
		return this.component;
	}
	
	@Override
	public Box[] generateBoxes(BoxContext context, DirectivePool parentDirectives, StyleGenerator styleGenerator) {
		DirectivePool styleDirectives = stylePoolGenerator.createStylePool(parentDirectives, styleGenerator);
		return WebBoxGenerator.generateBoxes(() -> boxGenerator.generateBoxes(context, styleDirectives, styleGenerator));
	}

}
