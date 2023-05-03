package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.document;

import com.github.webicitybrowser.thready.gui.directive.basics.pool.BasicDirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.BasicBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.layout.InnerDisplayLayout;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.layout.flow.FlowRootInnerDisplayLayout;
import com.github.webicitybrowser.threadyweb.tree.DocumentComponent;
import com.github.webicitybrowser.threadyweb.tree.ElementComponent;

public class DocumentUI implements ComponentUI {
	
	private final DocumentComponent component;
	
	private ComponentUI childComponentUI;

	public DocumentUI(Component component, ComponentUI parent) {
		this.component = (DocumentComponent) component;
	}

	@Override
	public DocumentComponent getComponent() {
		return this.component;
	}

	@Override
	public Box[] generateBoxes(BoxContext context, DirectivePool parentDirectives, StyleGenerator styleGenerator) {
		return component
			.getVisibleChild()
			.map(child -> getComponentUI(context, child))
			.map(ui -> ui.generateBoxes(context, parentDirectives, createChildStyleGenerator(ui, styleGenerator)))
			.map(childBoxes -> new Box[] { generateBlockRootBox(childBoxes) })
			.orElse(new Box[0]);
	}
	
	private Box generateBlockRootBox(Box[] childBoxes) {
		InnerDisplayLayout layout = new FlowRootInnerDisplayLayout();
		Box rootBox = new BasicBox(
			component, new BasicDirectivePool(),
			(box, children) -> layout.createRenderer(box, children));
		
		for (Box childBox: childBoxes) {
			rootBox.addChild(childBox);
		}
		
		return rootBox;
	}

	private ComponentUI getComponentUI(BoxContext context, ElementComponent child) {
		if (childComponentUI == null || childComponentUI.getComponent() != child) {
			this.childComponentUI = context.getLookAndFeel().createUIFor(child, this);
		}
		
		return this.childComponentUI;
	}
	
	private StyleGenerator createChildStyleGenerator(ComponentUI ui, StyleGenerator styleGenerator) {
		return styleGenerator.createChildStyleGenerators(new ComponentUI[] { ui })[0];
	}

}
