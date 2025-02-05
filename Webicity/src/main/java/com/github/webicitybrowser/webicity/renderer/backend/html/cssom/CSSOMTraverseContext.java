package com.github.webicitybrowser.webicity.renderer.backend.html.cssom;

import java.util.BitSet;
import java.util.List;
import java.util.Map;

public record CSSOMTraverseContext<T, U>(
	CSSOMParticipantTraverser<T, U> traverser,
	List<T> allParticipants,
	List<Integer> allParticipantsEnds,
	Map<T, Integer> participantIds,
	BitSet allParticipantsBitSet,
	Map<String, BitSet> typeMap,
	Map<String, BitSet> idMap,
	Map<String, BitSet> classMap
) {}