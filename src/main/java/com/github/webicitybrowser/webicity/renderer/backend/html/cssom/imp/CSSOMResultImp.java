package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.imp;

import java.util.Map;
import java.util.Set;

import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMResult;

public class CSSOMResultImp<T, U> implements CSSOMResult<T, U> {

	private final Map<T, CSSOMParticipantTracker<T, U>> matches;

	public CSSOMResultImp(Map<T, CSSOMParticipantTracker<T, U>> matches) {
		this.matches = matches;
	}

	@Override
	public Set<CSSOMNode<T, U>> getMatchingNodes(T value) {
		CSSOMParticipantTracker<T, U> tracker = matches.get(value);
		return tracker != null ?
			tracker.getMatchingNodes() :
			Set.of();
	}

}
