package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.styled;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.unit.StyledUnitContext;

public record StyledUnit(UIDisplay<?, ?, ?> display, StyledUnitContext context) implements RenderedUnit {

	@Override
	public AbsoluteSize fitSize() {
		return context.size();
	}
	
}
