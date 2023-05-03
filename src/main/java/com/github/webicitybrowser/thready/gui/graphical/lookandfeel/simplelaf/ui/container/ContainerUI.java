package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.Renderer;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.stage.style.SimpleStylePoolGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container.stage.box.ContainerUIBoxGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container.stage.render.ContainerRenderer;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.util.SimpleBoxGenerator;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public class ContainerUI implements ComponentUI {

	private final Component component;
	private final ComponentUI parent;
	private final ContainerUIBoxGenerator boxGenerator;
	
	private final SimpleStylePoolGenerator stylePoolGenerator;

	public ContainerUI(Component component, ComponentUI parent) {
		this.component = component;
		this.parent = parent;
		this.boxGenerator = new ContainerUIBoxGenerator(this, (box, children) -> createRenderer(box, children));
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
		DirectivePool styleDirectives = stylePoolGenerator.createStylePool(parentDirectives, styleGenerator);
		return SimpleBoxGenerator.generateBoxes(() -> boxGenerator.generateBoxes(context, styleDirectives, styleGenerator));
	}
	
	private Renderer createRenderer(Box box, Box[] children) {
		return new ContainerRenderer(box, children);
	}

}
