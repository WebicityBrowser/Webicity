package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public record ScrollBox(ScrollContext scrollContext, DirectivePool styleDirectives, Box innerBox) implements Box {
	
	@Override
	public UIDisplay<?, ?, ?> display() {
		return scrollContext.display();
	}

	@Override
	public Component owningComponent() {
		return scrollContext.componentUI().getComponent();
	}

	@Override
	public boolean isFluid() {
		return innerBox.isFluid();
	}

}
