package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.display.wrapper;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;

public record SimpleWrapperBox<U extends Box>(UIDisplay<?, ?, ?> display, U innerBox) implements Box {

	@Override
	public UIDisplay<?, ?, ?> display() {
		return display;
	}

	@Override
	public Context displayContext() {
		return innerBox.displayContext();
	}

	@Override
	public boolean isFluid() {
		return innerBox.isFluid();
	}
	
}
