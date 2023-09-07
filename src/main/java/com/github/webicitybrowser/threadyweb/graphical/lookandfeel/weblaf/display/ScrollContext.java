package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;

public class ScrollContext implements Context {

	private final UIDisplay<?, ?, ?> display;
	private final ComponentUI componentUI;
	private final Context innerContext;

	private AbsolutePosition scrollPosition = new AbsolutePosition(0, 0);

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
