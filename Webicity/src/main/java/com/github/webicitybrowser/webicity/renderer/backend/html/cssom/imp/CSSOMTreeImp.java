package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.imp;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.github.webicitybrowser.spec.css.selectors.combinator.Combinator;
import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMParticipantTraverser;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMTree;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.generator.DocumentStyleGenerator;

public class CSSOMTreeImp<T, U> implements CSSOMTree<T, U> {

	private final CSSOMNode<T, U> rootNode;
	private final CSSOMSelectorMatchUtil<T, U> selectorMatchUtil = new CSSOMSelectorMatchUtil<>();

	public CSSOMTreeImp(CSSOMNode<T, U> rootNode) {
		this.rootNode = rootNode;
	}

	@Override
	public void apply(T participant, CSSOMParticipantTraverser<T, U> traverser) {
		TraverseContext<T, U> context = new TraverseContext<>(
			traverser, new HashSet<>(), new HashMap<>(), new HashMap<>(), new HashMap<>());

		long time = System.currentTimeMillis();
		recursiveCollectElementData(participant, context);

		Map<CSSOMNode<T, U>, Set<T>> matched = new HashMap<>();
		matchSelectorParts(matched, context.allParticipants(), rootNode, context);
		System.out.println(matched.size());
		
		System.out.println("Time: " + (System.currentTimeMillis() - time));
		System.out.println(context.idMap().size());
	}

	private void recursiveCollectElementData(T participant, TraverseContext<T, U> context) {
		collectElementData(participant, context);
		for (T child: context.traverser().getChildren(participant)) {
			recursiveCollectElementData(child, context);
		}
	}

	private void collectElementData(T participant, TraverseContext<T, U> context) {
		context.allParticipants().add(participant);

		if (!(participant instanceof DocumentStyleGenerator generator)) return;
		Node domNode = generator.getDOMNode();
		if (!(domNode instanceof Element element)) return;

		String type = element.getLocalName();
		context.typeMap()
			.computeIfAbsent(type, _1 -> new HashSet<>(1))
			.add(participant);

		String id = element.getAttribute("id");
		if (id != null) {
			context.idMap()
				.computeIfAbsent(id, _1 -> new HashSet<>(1))
				.add(participant);
		}

		String className = element.getAttribute("class");
		if (className != null) {
			for (String classNamePart: className.split(" ")) {
				context.classMap()
					.computeIfAbsent(classNamePart, _1 -> new HashSet<>(1))
					.add(participant);
			}
		}
	}

	private void matchSelectorParts(Map<CSSOMNode<T, U>, Set<T>> matched, Set<T> current, CSSOMNode<T, U> node, TraverseContext<T, U> context) {
		Set<T> newMatched = selectorMatchUtil.matchIndividualSelectorPart(matched, current, node, context);
		matched.put(node, newMatched);
		for (CSSOMNode<T, U> child: node.getChildren()) {
			if (child.getSelectorPart() instanceof Combinator) {
				matchSelectorParts(matched, context.allParticipants(), child, context);
			} else {
				matchSelectorParts(matched, newMatched, child, context);
			}
		}
	}
	
}
