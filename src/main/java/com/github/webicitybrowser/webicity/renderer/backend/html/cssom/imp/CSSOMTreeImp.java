package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;

import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilterEntry;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMParticipant;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMResult;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMTree;

public class CSSOMTreeImp<T, U> implements CSSOMTree<T, U> {

	private final CSSOMNode<T, U> rootNode;

	public CSSOMTreeImp(CSSOMNode<T, U> rootNode) {
		this.rootNode = rootNode;
	}

	@Override
	public CSSOMResult<T, U> apply(CSSOMParticipant<T> participant) {
		Map<T, CSSOMParticipantTracker<T, U>> matches = new HashMap<>();
		PriorityQueue<CSSOMTask<T, U>> tasks = new PriorityQueue<>((a, b) -> a.priority() - b.priority());
		tasks.add(new CSSOMTask<>(0, participant, rootNode, (_1, child) -> List.of(child)));
		
		while (!tasks.isEmpty()) {
			executeNextTask(tasks, matches);
		}
		
		return new CSSOMResultImp<>(matches);
	}

	private void executeNextTask(PriorityQueue<CSSOMTask<T, U>> tasks, Map<T, CSSOMParticipantTracker<T, U>> matches) {
		CSSOMTask<T, U> task = tasks.poll();
		CSSOMParticipant<T> baseParticipant = task.participant();
		CSSOMFilter<T, U> filter = task.filter();
		List<CSSOMNode<T, U>> prematched = Optional.ofNullable(matches.get(baseParticipant.getValue()))
			.map(tracker -> tracker.getMatchingNodes())
			.orElse(List.of());
		for (CSSOMParticipant<T> matchedParticipant: filter.filter(prematched, baseParticipant)) {
			boolean alreadyMatched = !addMatch(matches, matchedParticipant, task.cssomNode());
			if (!alreadyMatched) {
				queueChildTasks(tasks, matchedParticipant, task.cssomNode(), matches);
			}
		}
	}

	private boolean addMatch(
		Map<T, CSSOMParticipantTracker<T, U>> matches, CSSOMParticipant<T> matchedParticipant, CSSOMNode<T, U> node
	) {
		return matches
			.computeIfAbsent(matchedParticipant.getValue(), v -> new CSSOMParticipantTracker<>())
			.addMatchingNode(node);
	}

	private void queueChildTasks(
		PriorityQueue<CSSOMTask<T, U>> tasks, CSSOMParticipant<T> matchedParticipant,
		CSSOMNode<T, U> cssomNode, Map<T, CSSOMParticipantTracker<T, U>> matches
	) {
		for (CSSOMFilterEntry<T, U> filter: cssomNode.getFilters()) {
			tasks.add(new CSSOMTask<>(filter.priority(), matchedParticipant, filter.cssomNode(), filter.filter()));
		}
	}

}
