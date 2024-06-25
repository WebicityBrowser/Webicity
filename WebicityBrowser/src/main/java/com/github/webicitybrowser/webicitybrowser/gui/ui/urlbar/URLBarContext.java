package com.github.webicitybrowser.webicitybrowser.gui.ui.urlbar;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.style.StyleContext;
import com.github.webicitybrowser.thready.gui.graphical.view.textfield.TextFieldContext;

public class URLBarContext implements Context {

	private final UIDisplay<?, ?, ?> display;
	private final ComponentUI componentUI;
	private final TextFieldContext textFieldContext;

	private DirectivePool styleDirectives;

	public URLBarContext(UIDisplay<?, ?, ?> display, ComponentUI componentUI, TextFieldContext textFieldContext) {
		this.display = display;
		this.componentUI = componentUI;
		this.textFieldContext = textFieldContext;
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

	public TextFieldContext textFieldContext() {
		return textFieldContext;
	}

}
