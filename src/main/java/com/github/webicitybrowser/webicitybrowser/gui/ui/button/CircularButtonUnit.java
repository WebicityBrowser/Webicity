package com.github.webicitybrowser.webicitybrowser.gui.ui.button;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.image.Image;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public record CircularButtonUnit(Image image, CircularButtonBox box) implements RenderedUnit {

	@Override
	public UIDisplay<?, ?, ?> display() {
		return box.display();
	}

	@Override
	public AbsoluteSize fitSize() {
		return new AbsoluteSize(22, 22);
	}

}
