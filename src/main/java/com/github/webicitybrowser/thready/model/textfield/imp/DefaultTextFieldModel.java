package com.github.webicitybrowser.thready.model.textfield.imp;

import com.github.webicitybrowser.thready.model.textfield.TextFieldListener;
import com.github.webicitybrowser.thready.model.textfield.TextFieldModel;

public class DefaultTextFieldModel implements TextFieldModel {
	
	private final StringBuilder currentText = new StringBuilder();
	private final TextFieldListener listener;
	
	private int cursorPos = 0;

	public DefaultTextFieldModel(TextFieldListener listener) {
		this.listener = listener;
	}

	@Override
	public String getText() {
		return currentText.toString();
	}

	@Override
	public void setText(String text) {
		currentText.setLength(0);
		currentText.append(text);
		cursorPos = text.length();
		invokeListener();
	}

	@Override
	public void setCursorPos(int pos) {
		this.cursorPos = pos;
		normalizeCursorPos();
		invokeListener();
	}

	@Override
	public int getCursorPos() {
		return this.cursorPos;
	}

	@Override
	public void delete(int run) {
		if (run >= 0) {
			deleteUpcoming(run);
		} else {
			deletePrevious(run);
		}
		invokeListener();
	}

	@Override
	public void insert(String text) {
		String ending = currentText.substring(cursorPos);
		currentText.setLength(cursorPos);
		currentText.append(text);
		currentText.append(ending);
		cursorPos += text.length();
		invokeListener();
	}
	
	@Override
	public void toggleInsertMode() {
		// TODO Auto-generated method stub
		invokeListener();
	}
	
	private void normalizeCursorPos() {
		cursorPos = Math.max(cursorPos, 0);
		cursorPos = Math.min(cursorPos, currentText.length());
	}
	
	private void deleteUpcoming(int run) {
		if (run + cursorPos > currentText.length()) {
			run = currentText.length() - cursorPos;
		}
		
		String ending = currentText.substring(cursorPos + run);
		currentText.setLength(cursorPos);
		currentText.append(ending);
	}
	
	private void deletePrevious(int run) {
		if (-run > cursorPos) {
			run = -cursorPos;
		}
		
		String ending = currentText.substring(cursorPos);
		currentText.setLength(cursorPos + run);
		currentText.append(ending);
		cursorPos += run;
	}
	
	private void invokeListener() {
		if (listener != null) {
			listener.onStateChanged();
		}
	}

}
