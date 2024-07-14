package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.styled;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.unit.StyledUnitContext;

public record StyledUnit(StyledUnitContext context, UIDisplay<?, ?, ?> display) implements RenderedUnit {

	@Override
	public Box box() {
		return context.innerUnit().box();
	}

	@Override
	public AbsoluteSize fitSize() {
		return context.size();
	}
	
}
