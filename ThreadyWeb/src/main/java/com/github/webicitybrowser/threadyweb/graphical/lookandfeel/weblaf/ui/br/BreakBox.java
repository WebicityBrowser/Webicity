package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.br;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;

public record BreakBox(Context displayContext) implements Box {
	
	@Override
	public boolean isFluid() {
		return true;
	}

}
