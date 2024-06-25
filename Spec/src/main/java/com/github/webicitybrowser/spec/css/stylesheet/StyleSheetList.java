package com.github.webicitybrowser.spec.css.stylesheet;

import com.github.webicitybrowser.spec.css.stylesheet.imp.StyleSheetListImp;

public interface StyleSheetList {

	CSSStyleSheet getItem(int index);
	
	int getLength();
	
	// TODO: Not a spec method
	void add(CSSStyleSheet styleSheet);

	void addUpdateListener(Runnable listener);

	static StyleSheetList create() {
		return new StyleSheetListImp();
	}
	
}
