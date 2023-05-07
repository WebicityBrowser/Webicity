package com.github.webicitybrowser.thready.model.textfield.imp;

import java.util.HashSet;
import java.util.Set;

import com.github.webicitybrowser.thready.model.textfield.TextFieldModel;
import com.github.webicitybrowser.thready.model.textfield.TextFieldModelListener;

public class DefaultTextFieldModel implements TextFieldModel {
	
	private final StringBuilder currentText = new StringBuilder();
	private final Set<TextFieldModelListener> listeners = new HashSet<>();
	
	private int cursorPos = 0;

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
	public void replace(String text) {
		int endPos = cursorPos + text.length();
		if (endPos > currentText.length()) {
			endPos = currentText.length();
		}
		String ending = currentText.substring(endPos);
		currentText.setLength(cursorPos);
		currentText.append(text);
		currentText.append(ending);
		cursorPos += text.length();
		invokeListener();
	}
	
	@Override
	public void addListener(TextFieldModelListener listener) {
		listeners.add(listener);
	}
	
	@Override
	public void removeListener(TextFieldModelListener listener) {
		listeners.remove(listener);
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
		for (TextFieldModelListener listener: listeners) {
			listener.onStateChanged();
		}
	}

}
