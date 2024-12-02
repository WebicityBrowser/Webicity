package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display.scroll;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;

public record ScrollBox(ScrollContext scrollContext, DirectivePool styleDirectives, Box innerBox) implements Box {
	
	@Override
	public UIDisplay<?, ?, ?> display() {
		return scrollContext.display();
	}

	@Override
	public ComponentUI componentUI() {
		return scrollContext.componentUI();
	}

	@Override
	public boolean isFluid() {
		return innerBox.isFluid();
	}

}
