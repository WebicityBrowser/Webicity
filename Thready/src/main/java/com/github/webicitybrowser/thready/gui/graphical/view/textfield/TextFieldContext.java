package com.github.webicitybrowser.thready.gui.graphical.view.textfield;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.viewmodel.textfield.TextFieldViewModel;

public class TextFieldContext {

	private final ComponentUI owningComponentUI;
	private final TextFieldViewModel viewModel;
	
	private TextFieldCursorState lastCursorState = TextFieldCursorState.INACTIVE;

	public TextFieldContext(ComponentUI owningComponentUI, TextFieldViewModel viewModel) {
		this.owningComponentUI = owningComponentUI;
		this.viewModel = viewModel;
	}
	
	public TextFieldViewModel getViewModel() {
		return this.viewModel;
	}
	
	public TextFieldCursorState getLastCursorState() {
		return this.lastCursorState;
	}
	
	public void setLastCursorState(TextFieldCursorState lastCursorState) {
		this.lastCursorState = lastCursorState;
	}
	
	public ComponentUI getOwningComponentUI() {
		return this.owningComponentUI;
	}
	
}
