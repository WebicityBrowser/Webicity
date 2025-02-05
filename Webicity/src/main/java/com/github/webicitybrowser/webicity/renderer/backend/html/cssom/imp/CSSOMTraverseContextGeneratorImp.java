package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.imp;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;

import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMParticipantTraverser;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMTraverseContext;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMTraverseContextGenerator;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.generator.DocumentStyleGenerator;

public class CSSOMTraverseContextGeneratorImp<T, U> implements CSSOMTraverseContextGenerator<T, U> {

	@Override
	public CSSOMTraverseContext<T, U> apply(T participant, CSSOMParticipantTraverser<T, U> traverser) {
		CSSOMTraverseContext<T, U> context = new CSSOMTraverseContext<>(
			traverser, new ArrayList<>(), new ArrayList<>(), new HashMap<>(),
			new BitSet(), new HashMap<>(), new HashMap<>(), new HashMap<>());

		recursiveCollectElementData(participant, context);

		return context;
	}
	
	private void recursiveCollectElementData(T participant, CSSOMTraverseContext<T, U> context) {
		int myStart = collectElementData(participant, context);
		if (myStart != -1) {
			context.allParticipantsEnds().add(myStart);
			context.participantIds().put(participant, myStart);
		}
		for (T child: context.traverser().getChildren(participant)) {
			recursiveCollectElementData(child, context);
		}
		if (myStart != -1) {
			context.allParticipantsEnds().set(myStart, context.allParticipants().size() - 1);
		}
	}

	private int collectElementData(T participant, CSSOMTraverseContext<T, U> context) {
		int bitSetIndex = context.allParticipants().size();

		if (!(participant instanceof DocumentStyleGenerator generator)) return -1;
		Node domNode = generator.getDOMNode();
		if (!(domNode instanceof Element element)) return -1;

		context.allParticipants().add(participant);
		context.allParticipantsBitSet().set(bitSetIndex);

		String type = element.getLocalName();
		context.typeMap()
			.computeIfAbsent(type, _1 -> new BitSet())
			.set(bitSetIndex);

		String id = element.getAttribute("id");
		if (id != null) {
			context.idMap()
				.computeIfAbsent(id, _1 -> new BitSet())
				.set(bitSetIndex);
		}

		String className = element.getAttribute("class");
		if (className != null) {
			for (String classNamePart: className.split(" ")) {
				context.classMap()
					.computeIfAbsent(classNamePart, _1 -> new BitSet())
					.set(bitSetIndex);
			}
		}

		return bitSetIndex;
	}

}
