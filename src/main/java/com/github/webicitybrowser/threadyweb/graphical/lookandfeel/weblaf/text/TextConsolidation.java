package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text;

import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text.imp.TextConsolidationImp;

public interface TextConsolidation {

	void addText(Object textOwner, String text);
	
	ConsolidatedCollapsibleTextView getTextView();
	
	String readNextText(Object textOwner);

	public static TextConsolidation create() {
		return new TextConsolidationImp();
	}
	
}
