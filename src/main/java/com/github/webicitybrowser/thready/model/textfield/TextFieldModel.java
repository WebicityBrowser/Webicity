package com.github.webicitybrowser.thready.model.textfield;

import com.github.webicitybrowser.thready.model.textfield.imp.DefaultTextFieldModel;

public interface TextFieldModel {

	String getText();
	
	void setText(String text);
	
	void setCursorPos(int pos);
	
	int getCursorPos();
	
	void delete(int run);
	
	void insert(String text);
	
	void toggleInsertMode();
	
	public static TextFieldModel create() {
		return new DefaultTextFieldModel(null);
	}

	static TextFieldModel create(TextFieldListener listener) {
		return new DefaultTextFieldModel(listener);
	}
	
}
