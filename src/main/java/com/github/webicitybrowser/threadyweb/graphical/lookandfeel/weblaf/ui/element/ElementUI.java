package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element;

import com.github.webicitybrowser.thready.gui.directive.core.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.SolidRenderer;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.box.ElementUIBoxGenerator;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.render.ElementBlockRenderer;
import com.github.webicitybrowser.threadyweb.tree.ElementComponent;

public class ElementUI implements ComponentUI {

	private final ElementComponent component;
	private final ElementUIBoxGenerator boxGenerator;

	public ElementUI(Component component, ComponentUI parent) {
		this.component = (ElementComponent) component;
		this.boxGenerator = new ElementUIBoxGenerator(
			this.component, parent, (box, children) -> createRenderer(box, children));
	}
	
	@Override
	public Component getComponent() {
		return this.component;
	}
	
	@Override
	public Box[] generateBoxes(BoxContext context) {
		DirectivePool styleDirectives = component.getStyleDirectives();
		return boxGenerator.generateBoxes(context, styleDirectives);
	}
	
	private SolidRenderer createRenderer(Box box, Box[] children) {
		return new ElementBlockRenderer(box, children);
	}

}
