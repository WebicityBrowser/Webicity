package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.github.webicitybrowser.spec.css.rule.CSSRuleList;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.thready.gui.directive.basics.pool.DirectiveDeriver;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.threadyweb.tree.ElementComponent;
import com.github.webicitybrowser.threadyweb.tree.WebComponent;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMMappedRuleList;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;

public class DocumentStyleGenerator implements StyleGenerator {
	
	private static final NodeComparator<CSSRuleList> NODE_COMPARATOR = new NodeComparator<>();

	private final Node node;
	private final List<CSSOMNode<DocumentStyleGenerator, CSSRuleList>> matchingCSSOMNodes = new ArrayList<>(1);
	private final DocumentStyleGenerator parent;
	private final List<DocumentStyleGenerator> children = new ArrayList<>(1);
	private final Function<CSSRuleList, CSSOMMappedRuleList<Directive>> ruleListMapper;
	private final Map<Class<? extends Directive>, DirectiveDeriver<? extends Directive>> derivers;

	private DocumentDirectivePool styleDirectives;

	public DocumentStyleGenerator(
		Node node, DocumentStyleGenerator parent, Function<CSSRuleList, CSSOMMappedRuleList<Directive>> ruleListMapper,
		Map<Class<? extends Directive>, DirectiveDeriver<? extends Directive>> derivers
	) {
		this.node = node;
		this.parent = parent;
		this.ruleListMapper = ruleListMapper;
		this.derivers = derivers;
		generateChildren();
	}

	@Override
	public StyleGenerator[] createChildStyleGenerators(ComponentUI[] childUIs) {
		// TODO: We need to make sure comments are excluded
		StyleGenerator[] childGenerators = new StyleGenerator[childUIs.length];

		int i = 0;
		for (DocumentStyleGenerator childGenerator: children) {
			if (i >= childUIs.length) {
				break;
			}
			ComponentUI childUI = childUIs[i];
			WebComponent childComponent = (WebComponent) childUI.getComponent();
			if (childComponent.getNode() != childGenerator.getDOMNode()) {
				continue;
			}
			childGenerators[i] = childGenerator;
			
			CSSRuleList ruleList = childUI.getComponent() instanceof ElementComponent elementComponent ?
				elementComponent.getComponentRules() :
				CSSRuleList.createEmpty();
			
			childGenerator.generateStyleDirectives(ruleList);
			i++;
		}

		assert i == childGenerators.length;
		
		return childGenerators;
	}
	
	@Override
	public DocumentDirectivePool getStyleDirectives() {
		return this.styleDirectives;
	}

	public DocumentStyleGenerator getParent() {
		return parent;
	}

	public DocumentStyleGenerator[] getChildren() {
		return children.toArray(DocumentStyleGenerator[]::new);
	}

	public Node getDOMNode() {
		return node;
	}

	public void generateStyleDirectives(CSSRuleList componentRules) {
		Collections.sort(matchingCSSOMNodes, NODE_COMPARATOR);

		List<CSSOMMappedRuleList<Directive>> matchingRules = new ArrayList<>(0);
		if (componentRules.getLength() != 0) {
			matchingRules.add(ruleListMapper.apply(componentRules));
		}
		for (CSSOMNode<DocumentStyleGenerator, CSSRuleList> cssomNode: matchingCSSOMNodes) {
			for (CSSRuleList ruleList: cssomNode.getNodeProperties()) {
				matchingRules.add(ruleListMapper.apply(ruleList));
			}
		}

		this.styleDirectives = new DocumentDirectivePool(
			parent == null ? null : parent.getStyleDirectives(),
			matchingRules, derivers);
	}

	public Collection<CSSOMNode<DocumentStyleGenerator, CSSRuleList>> getMatchingCSSOMNodes() {
		return this.matchingCSSOMNodes;
	}

	public void addMatchingNode(CSSOMNode<DocumentStyleGenerator, CSSRuleList> baseNode) {
		this.matchingCSSOMNodes.add(baseNode);
	}

	private void generateChildren() {
		for (Node child: node.getChildNodes()) {
			children.add(new DocumentStyleGenerator(child, this, ruleListMapper, derivers));
		}
	}

	private static class NodeComparator<T> implements Comparator<CSSOMNode<DocumentStyleGenerator, T>> {
		@Override
		public int compare(CSSOMNode<DocumentStyleGenerator, T> o1, CSSOMNode<DocumentStyleGenerator, T> o2) {
			if (o1.getSpecificity() == o2.getSpecificity()) {
				return 0;
			} else if (o1.getSpecificity() == null) {
				return -1;
			} else if (o2.getSpecificity() == null) {
				return 1;
			}
			return -o1.getSpecificity().compareTo(o2.getSpecificity());
		}	
	}

}
