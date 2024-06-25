package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display.scroll;

import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.style.StyleContext;

public class ScrollContext implements Context {

	private final UIDisplay<?, ?, ?> display;
	private final ComponentUI componentUI;
	private final Context innerContext;

	private AbsolutePosition scrollPosition = AbsolutePosition.ZERO_POSITION;

	public ScrollContext(UIDisplay<?, ?, ?> display, ComponentUI componentUI, Context innerContext) {
		this.display = display;
		this.componentUI = componentUI;
		this.innerContext = innerContext;
	}

	@Override
	public UIDisplay<?, ?, ?> display() {
		return display;
	}

	@Override
	public ComponentUI componentUI() {
		return componentUI;
	}

	@Override
	public DirectivePool styleDirectives() {
		return innerContext.styleDirectives();
	}

	@Override
	public List<Context> children() {
		return innerContext.children();
	}

	@Override
	public void regenerateStyling(DirectivePool styleDirectives, StyleContext styleContext) {
		innerContext.regenerateStyling(styleDirectives, styleContext);
	}

	public Context innerContext() {
		return innerContext;
	}

	public AbsolutePosition scrollPosition() {
		return scrollPosition;
	}

	public void setScrollPosition(AbsolutePosition scrollPosition) {
		this.scrollPosition = scrollPosition;
	}

}
