package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.filter;

import java.util.List;

import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMParticipant;

public class ChildFilter<T, U> implements CSSOMFilter<T, U> {

	@Override
	public List<CSSOMParticipant<T>> filter(List<CSSOMNode<T, U>> prematched, CSSOMParticipant<T> item) {
		return item.getChildren();
	}

}
