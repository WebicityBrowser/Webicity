package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.imp;

import java.util.Map;
import java.util.Set;

import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMParticipantTraverser;

public record TraverseContext<T, U>(
	CSSOMParticipantTraverser<T, U> traverser,
	Set<T> allParticipants,
	Map<String, Set<T>> typeMap,
	Map<String, Set<T>> idMap,
	Map<String, Set<T>> classMap
) {}