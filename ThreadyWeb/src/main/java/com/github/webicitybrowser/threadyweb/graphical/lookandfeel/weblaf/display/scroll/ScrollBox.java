package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display.scroll;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;

public record ScrollBox(ScrollContext displayContext, Box innerBox) implements Box {
	
	@Override
	public boolean isFluid() {
		return innerBox.isFluid();
	}

}
