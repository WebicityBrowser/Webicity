package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMResult;

public class CSSOMResultImp<T, U> implements CSSOMResult<T, U> {

	private final Map<T, CSSOMParticipantTracker<T, U>> matches;

	public CSSOMResultImp(Map<T, CSSOMParticipantTracker<T, U>> matches) {
		this.matches = matches;
	}

	@Override
	public List<CSSOMNode<T, U>> getMatchingNodes(T value) {
		return Optional.ofNullable(matches.get(value))
			.map(tracker -> tracker.getMatchingNodes())
			.orElse(new ArrayList<>(0));
	}

}
