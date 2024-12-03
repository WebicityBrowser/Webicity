package com.github.webicitybrowser.threadyweb.graphical.layout.adjusted;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public record AdjustedUnit(RenderedUnit renderedUnit, Rectangle adjustedBounds) implements RenderedUnit {

	private static final AdjustedDisplay ADJUSTED_DISPLAY = new AdjustedDisplay();

	@Override
	public UIDisplay<?, ?, ?> display() {
		return ADJUSTED_DISPLAY;
	}

	@Override
	public AbsoluteSize fitSize() {
		return adjustedBounds.size();
	}

	@Override
	public DirectivePool styleDirectives() {
		return renderedUnit.styleDirectives();
	}

}
