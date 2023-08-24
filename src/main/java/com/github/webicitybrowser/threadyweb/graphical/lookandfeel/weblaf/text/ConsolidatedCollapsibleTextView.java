package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text;

import java.util.List;

import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text.imp.ConsolidatedCollapsibleTextViewImp;

public interface ConsolidatedCollapsibleTextView {

	boolean atEnd();
	
	boolean atStart();
	
	void advance();
	
	void regress();
	
	int getCurrentCodepoint();
	
	void delete();
	
	void replace(char ch);

	void restart();
	
	public static ConsolidatedCollapsibleTextView create(List<String> backingStrings) {
		return new ConsolidatedCollapsibleTextViewImp(backingStrings);
	}
	
}
