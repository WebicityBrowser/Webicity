package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public record ScrollUnit(ScrollBox box, AbsoluteSize fitSize, RenderedUnit innerUnit) implements RenderedUnit {

	@Override
	public UIDisplay<?, ?, ?> display() {
		return box.display();
	}

}
