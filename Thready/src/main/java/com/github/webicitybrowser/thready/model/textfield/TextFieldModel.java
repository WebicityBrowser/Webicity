package com.github.webicitybrowser.thready.model.textfield;

import com.github.webicitybrowser.thready.model.textfield.imp.DefaultTextFieldModel;

public interface TextFieldModel {

	String getText();
	
	void setText(String text);
	
	void setCursorPos(int pos);
	
	int getCursorPos();
	
	void delete(int run);
	
	void insert(String text);
	
	void replace(String text);

	void addListener(TextFieldModelListener listener);
	
	void removeListener(TextFieldModelListener listener);
	
	public static TextFieldModel create() {
		return new DefaultTextFieldModel();
	}
	
}
