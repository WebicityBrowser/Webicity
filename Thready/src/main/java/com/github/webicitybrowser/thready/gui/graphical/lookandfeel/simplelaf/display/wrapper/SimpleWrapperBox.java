package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.display.wrapper;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public record SimpleWrapperBox<U extends Box>(Component owningComponent, DirectivePool styleDirectives, UIDisplay<?, ?, ?> display, U innerBox) implements Box {

	@Override
	public boolean isFluid() {
		return innerBox.isFluid();
	}
	
}
