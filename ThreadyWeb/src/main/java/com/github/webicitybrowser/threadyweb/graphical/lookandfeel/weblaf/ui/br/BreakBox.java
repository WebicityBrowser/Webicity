package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.br;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;

public record BreakBox(UIDisplay<?, ?, ?> display, ComponentUI componentUI, DirectivePool styleDirectives) implements Box {
	
	@Override
	public boolean isFluid() {
		return true;
	}

}
