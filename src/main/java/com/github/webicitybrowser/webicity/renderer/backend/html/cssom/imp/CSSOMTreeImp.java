package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.imp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilterEntry;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMParticipantTraverser;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMResult;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMTree;

public class CSSOMTreeImp<T, U> implements CSSOMTree<T, U> {

	private final CSSOMNode<T, U> rootNode;

	public CSSOMTreeImp(CSSOMNode<T, U> rootNode) {
		this.rootNode = rootNode;
	}

	@Override
	public CSSOMResult<T, U> apply(T participant, CSSOMParticipantTraverser<T> traverser) {
		Map<T, CSSOMParticipantTracker<T, U>> matches = new HashMap<>();
		PriorityQueue<CSSOMTask<T, U>> tasks = new PriorityQueue<>((a, b) -> a.priority() - b.priority());
		tasks.add(new CSSOMTask<>(0, participant, rootNode, (_1, child, _2) -> List.of(child)));
		TreeContext<T, U> context = new TreeContext<>(tasks, matches, traverser);
		
		while (!tasks.isEmpty()) {
			executeNextTask(context);
		}
		
		return new CSSOMResultImp<>(matches);
	}

	private void executeNextTask(TreeContext<T, U> context) {
		CSSOMTask<T, U> task = context.tasks().poll();
		T baseParticipant = task.participant();
		CSSOMFilter<T, U> filter = task.filter();
		int priority = task.priority();
		CSSOMNode<T, U> cssomNode = task.cssomNode();
		exploreNode(context, baseParticipant, filter, cssomNode, priority);
	}
	
	private void exploreNode(
		TreeContext<T, U> context, T baseParticipant, CSSOMFilter<T, U> filter, CSSOMNode<T, U> cssomNode, int priority
	) {
		CSSOMParticipantTracker<T, U> tracker = context.matches().get(baseParticipant);
		Set<CSSOMNode<T, U>> prematched = tracker == null ?
			Set.of() :
			tracker.getMatchingNodes();
		for (T matchedParticipant: filter.filter(prematched, baseParticipant, context.traverser)) {
			boolean alreadyMatched = !addMatch(context, matchedParticipant, cssomNode);
			if (!alreadyMatched) {
				exploreChildNodes(context, matchedParticipant, cssomNode, priority);
			}
		}
	}

	private boolean addMatch(
		TreeContext<T, U> context, T matchedParticipant, CSSOMNode<T, U> node
	) {
		return context.matches()
			.computeIfAbsent(matchedParticipant, v -> new CSSOMParticipantTracker<>())
			.addMatchingNode(node);
	}

	private void exploreChildNodes(
		TreeContext<T, U> context, T matchedParticipant, CSSOMNode<T, U> cssomNode, int priority
	) {
		for (CSSOMFilterEntry<T, U> filter: cssomNode.getFilters()) {
			if (filter.priority() != priority) {
				context.tasks().add(new CSSOMTask<>(filter.priority(), matchedParticipant, filter.cssomNode(), filter.filter()));
			} else {
				exploreNode(context, matchedParticipant, filter.filter(), filter.cssomNode(), priority);
			}
		}
	}

	private record TreeContext<T, U>(
		PriorityQueue<CSSOMTask<T, U>> tasks, Map<T, CSSOMParticipantTracker<T, U>> matches, CSSOMParticipantTraverser<T> traverser
	) {}
	
}
