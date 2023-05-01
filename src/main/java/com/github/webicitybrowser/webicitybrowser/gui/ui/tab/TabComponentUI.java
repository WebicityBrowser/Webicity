package com.github.webicitybrowser.webicitybrowser.gui.ui.tab;

import com.github.webicitybrowser.thready.gui.directive.core.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.BasicBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.stage.style.SimpleStylePoolGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.util.SimpleBoxGenerator;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.webicitybrowser.gui.binding.component.tab.TabComponent;

public class TabComponentUI implements ComponentUI {

	private final TabComponent component;
	private final SimpleStylePoolGenerator stylePoolGenerator;

	public TabComponentUI(Component component, ComponentUI parent) {
		this.component = (TabComponent) component;
		this.stylePoolGenerator = new SimpleStylePoolGenerator(component.getStyleDirectives());
	}
	
	@Override
	public Component getComponent() {
		return this.component;
	}

	@Override
	public Box[] generateBoxes(BoxContext context, DirectivePool parentDirectives, StyleGenerator styleGenerator) {
		DirectivePool directives = stylePoolGenerator.createStylePool(parentDirectives, styleGenerator);
		
		return SimpleBoxGenerator.generateBoxes(() -> new Box[] {
			new BasicBox(
				directives,
				(box, children) -> new TabComponentRenderer(box, component))
		});
	}

}
