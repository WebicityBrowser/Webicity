package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.imp;

import java.util.Map;

import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMParticipantTraverser;

public record TraverseContext<T, U>(
	CSSOMParticipantTraverser<T> traverser,
	Map<T, CSSOMParticipantTracker<T, U>> matches,
	CSSOMTips<T, U> tips
) {

	public CSSOMParticipantTracker<T, U> matchingNodes(T participant) {
		return matches.computeIfAbsent(participant, _1 -> new CSSOMParticipantTracker<>());
	}

}