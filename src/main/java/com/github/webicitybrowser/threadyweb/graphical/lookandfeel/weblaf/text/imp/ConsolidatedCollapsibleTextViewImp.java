package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text.imp;

import java.util.List;

import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text.ConsolidatedCollapsibleTextView;

public class ConsolidatedCollapsibleTextViewImp implements ConsolidatedCollapsibleTextView {
	
	// TODO: Special handling for empty backingStrings
	
	private final List<String> backingStrings;
	
	private int firstNonEmptyLineIndex = 0;
	private int lineCursor = 0;
	private int textCursor = 0;

	public ConsolidatedCollapsibleTextViewImp(List<String> backingStrings) {
		this.backingStrings = backingStrings;
		updateFirstNonEmptyLine();
	}

	@Override
	public boolean atEnd() {
		if (backingStrings.isEmpty()) return true;
		String lastString = backingStrings.get(backingStrings.size() - 1);
		return textCursor == lastString.length() && lineCursor == backingStrings.size() - 1;
	}

	@Override
	public boolean atStart() {
		return lineCursor <= firstNonEmptyLineIndex && textCursor == 0;
	}

	@Override
	public void advance() {
		textCursor++;
		advanceIfStringEnd();
	}

	@Override
	public void regress() {
		while (!atStart() && textCursor == 0) {
			lineCursor--;
			textCursor = getCurrentStringLength();
		}
		textCursor--;
	}

	@Override
	public int getCurrentCodepoint() {
		return backingStrings.get(lineCursor).codePointAt(textCursor);
	}

	@Override
	public void delete() {
		String currentString = backingStrings.get(lineCursor);
		String replaced = currentString.substring(0, textCursor) + currentString.substring(textCursor + 1);
		backingStrings.set(lineCursor, replaced);
		advanceIfStringEnd();
		updateFirstNonEmptyLine();
	}

	@Override
	public void replace(char ch) {
		String currentString = backingStrings.get(lineCursor);
		String replaced = currentString.substring(0, textCursor) + ch + currentString.substring(textCursor + 1);
		backingStrings.set(lineCursor, replaced);
		textCursor++;
		advanceIfStringEnd();
	}

	@Override
	public void restart() {
		lineCursor = firstNonEmptyLineIndex;
		textCursor = 0;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (String string : backingStrings) {
			builder.append(string);
		}
		return builder.toString();
	}
	
	private void advanceIfStringEnd() {
		while (!atEnd() && atEndOfCurrentString()) {
			textCursor = 0;
			lineCursor++;
		}
	}
	
	private boolean atEndOfCurrentString() {
		return textCursor == getCurrentStringLength();
	}
	
	private int getCurrentStringLength() {
		return backingStrings.get(lineCursor).length();
	}
	
	private void updateFirstNonEmptyLine() {
		while (firstNonEmptyLineIndex < backingStrings.size() - 1 && backingStrings.get(firstNonEmptyLineIndex).isEmpty() && !atEnd()) {
			firstNonEmptyLineIndex++;
		}
	}

}
