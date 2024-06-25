package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.image;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.style.StyleContext;

public class ImageContext implements Context {

	private final UIDisplay<?, ?, ?> display;
	private final ComponentUI componentUI;

	private DirectivePool styleDirectives;

	public ImageContext(UIDisplay<?, ?, ?> display, ComponentUI componentUI) {
		this.display = display;
		this.componentUI = componentUI;
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
		return styleDirectives;
	}

	@Override
	public void regenerateStyling(DirectivePool styleDirectives, StyleContext styleContext) {
		this.styleDirectives = styleDirectives;
	}

}
