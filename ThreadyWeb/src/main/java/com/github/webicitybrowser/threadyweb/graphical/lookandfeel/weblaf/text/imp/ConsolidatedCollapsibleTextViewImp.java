package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text.imp;

import java.util.List;

import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text.ConsolidatedCollapsibleTextView;

public class ConsolidatedCollapsibleTextViewImp implements ConsolidatedCollapsibleTextView {
	
	// TODO: Special handling for empty backingStrings
	
	private final List<StringBuilder> backingStrings;
	
	private int firstNonEmptyLineIndex = 0;
	private int lineCursor = 0;
	private int textCursor = 0;

	public ConsolidatedCollapsibleTextViewImp(List<StringBuilder> backingStrings) {
		this.backingStrings = backingStrings;
		updateFirstNonEmptyLine();
	}

	@Override
	public boolean atEnd() {
		if (backingStrings.isEmpty()) return true;
		StringBuilder lastString = backingStrings.get(backingStrings.size() - 1);
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
		StringBuilder currentString = backingStrings.get(lineCursor);
		currentString.deleteCharAt(textCursor);
		advanceIfStringEnd();
		updateFirstNonEmptyLine();
	}

	@Override
	public void replace(char ch) {
		StringBuilder currentString = backingStrings.get(lineCursor);
		currentString.setCharAt(textCursor, ch);
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
		for (StringBuilder string : backingStrings) {
			builder.append(string.toString());
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
