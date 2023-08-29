package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public record ElementUnit(UIDisplay<?, ?, ?> display, DirectivePool styleDirectives, LayoutResult layoutResults) implements RenderedUnit {

	@Override
	public AbsoluteSize fitSize() {
		return layoutResults.fitSize();
	}
	
}
