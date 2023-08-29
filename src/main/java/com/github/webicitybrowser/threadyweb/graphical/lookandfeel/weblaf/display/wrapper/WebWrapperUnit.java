package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display.wrapper;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public record WebWrapperUnit<V extends RenderedUnit>(UIDisplay<?, ?, ?> display, WebWrapperBox box, V innerUnit) implements RenderedUnit {

	@Override
	public AbsoluteSize fitSize() {
		return innerUnit.fitSize();
	}

}
