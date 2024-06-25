package com.github.webicitybrowser.webicitybrowser.gui.ui.tab;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.webicitybrowser.gui.binding.component.tab.TabComponent;

public record TabBox(TabContext context) implements Box {

	@Override
	public UIDisplay<?, ?, ?> display() {
		return context.display();
	}

	@Override
	public TabComponent owningComponent() {
		return (TabComponent) context.componentUI().getComponent();
	}

	public DirectivePool styleDirectives() {
		return context.styleDirectives();
	}

}
