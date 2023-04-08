package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container;

import com.github.webicitybrowser.thready.gui.directive.core.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.SolidRenderer;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container.stage.box.ContainerUIBoxGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container.stage.render.ContainerRenderer;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public class ContainerUI implements ComponentUI {

	private final Component component;
	private final ContainerUIBoxGenerator boxGenerator;

	public ContainerUI(Component component, ComponentUI parent) {
		this.component = component;
		this.boxGenerator = new ContainerUIBoxGenerator(parent, (box, children) -> createRenderer(box, children));
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
		return new ContainerRenderer(box, children);
	}

}
