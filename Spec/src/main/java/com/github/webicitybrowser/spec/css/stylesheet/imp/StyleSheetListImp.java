package com.github.webicitybrowser.spec.css.stylesheet.imp;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.spec.css.stylesheet.CSSStyleSheet;
import com.github.webicitybrowser.spec.css.stylesheet.StyleSheetList;

public class StyleSheetListImp implements StyleSheetList {

	private List<CSSStyleSheet> styleSheets = new ArrayList<>();
	private List<Runnable> updateListeners = new ArrayList<>();
	
	@Override
	public CSSStyleSheet getItem(int index) {
		return styleSheets.get(index);
	}

	@Override
	public int getLength() {
		return styleSheets.size();
	}

	@Override
	public void add(CSSStyleSheet styleSheet) {
		styleSheets.add(styleSheet);
		updateListeners.forEach(Runnable::run);
	}

	@Override
	public void addUpdateListener(Runnable listener) {
		updateListeners.add(listener);
	}

}
