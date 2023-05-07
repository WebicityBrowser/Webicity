package com.github.webicitybrowser.thready.gui.graphical.viewmodel.textfield;

import com.github.webicitybrowser.thready.model.textfield.TextFieldModel;

public class TextFieldViewModel {

	private final TextFieldModel model;
	private final TextFieldViewModelListener listener;
	
	private boolean isReplaceMode = false;
	private boolean isFocused = false;

	public TextFieldViewModel(TextFieldModel model, TextFieldViewModelListener listener) {
		this.model = model;
		this.listener = listener;
		model.addListener(() -> listener.onStateChanged());
	}
	
	public String getText() {
		return model.getText();
	}
	
	public void setCursorPos(int pos) {
		model.setCursorPos(pos);
	}
	
	public int getCursorPos() {
		return model.getCursorPos();
	}
	
	public void delete(int run) {
		model.delete(run);
	}
	
	public void insert(String text) {
		if (isReplaceMode) {
			model.replace(text);
		} else {
			model.insert(text);
		}
	}
	
	public void toggleReplaceMode() {
		this.isReplaceMode = !isReplaceMode;
		listener.onStateChanged();
	}
	
	public boolean isReplaceMode() {
		return this.isReplaceMode;
	}

	public void moveCursor(int mov) {
		model.setCursorPos(model.getCursorPos() + mov);
	}

	public void setCursorToEnd() {
		model.setCursorPos(model.getText().length());
	}

	public void submit() {
		listener.onSubmit();
	}

	public void setFocused(boolean focused) {
		this.isFocused = focused;
		listener.onStateChanged();
	}
	
	public boolean isFocused() {
		return isFocused;
	}
	
}
