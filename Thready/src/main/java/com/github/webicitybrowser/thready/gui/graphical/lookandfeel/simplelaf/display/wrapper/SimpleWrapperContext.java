package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.display.wrapper;

import java.util.List;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.style.StyleContext;

public record SimpleWrapperContext<T extends Context>(UIDisplay<?, ?, ?> display, ComponentUI componentUI, T childContext) implements Context {

	@Override
	public DirectivePool styleDirectives() {
		return childContext.styleDirectives();
	}

	@Override
	public List<Context> children() {
		return childContext.children();
	}

	@Override
	public void regenerateStyling(DirectivePool styleDirectives, StyleContext styleContext) {
		childContext.regenerateStyling(styleDirectives, styleContext);
	}

}
