package com.github.webicitybrowser.webicitybrowser.gui.ui.urlbar;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.context.GenericContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.view.textfield.TextFieldContext;

public class URLBarContext extends GenericContext{

	private final TextFieldContext textFieldContext;

	public URLBarContext(UIDisplay<?, ?, ?> display, ComponentUI componentUI, TextFieldContext textFieldContext) {
		super(display, componentUI);
		this.textFieldContext = textFieldContext;
	}

	public TextFieldContext textFieldContext() {
		return textFieldContext;
	}

}
