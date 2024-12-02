package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.display.wrapper;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;

public record SimpleWrapperBox<U extends Box>(ComponentUI componentUI, DirectivePool styleDirectives, UIDisplay<?, ?, ?> display, U innerBox) implements Box {

	@Override
	public boolean isFluid() {
		return innerBox.isFluid();
	}
	
}
