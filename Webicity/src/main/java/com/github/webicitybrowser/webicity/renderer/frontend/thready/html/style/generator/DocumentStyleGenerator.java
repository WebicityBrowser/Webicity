package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.github.webicitybrowser.spec.css.rule.CSSRuleList;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.threadyweb.tree.ElementComponent;
import com.github.webicitybrowser.threadyweb.tree.WebComponent;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMPropertyResolver;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMDeclarationParser;

public class DocumentStyleGenerator implements StyleGenerator {
	
	private static final NodeComparator<CSSRuleList> NODE_COMPARATOR = new NodeComparator<>();

	private final Node node;
	private final List<CSSOMNode<DocumentStyleGenerator, CSSRuleList>> matchingCSSOMNodes = new ArrayList<>(1);
	private final DocumentStyleGenerator parent;
	private final List<DocumentStyleGenerator> children = new ArrayList<>(1);
	private final CSSOMDeclarationParser declarationParser;

	private CSSOMPropertyResolver propertyResolver;
	private DirectivePool styleDirectives;

	public DocumentStyleGenerator(Node node, DocumentStyleGenerator parent, CSSOMDeclarationParser declarationParser) {
		this.node = node;
		this.parent = parent;
		this.declarationParser = declarationParser;
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
			
			childGenerator.generateStyleDirectives(this.propertyResolver, ruleList);
			i++;
		}

		assert i == childGenerators.length;
		
		return childGenerators;
	}
	
	@Override
	public DirectivePool getStyleDirectives() {
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

	public void generateStyleDirectives(CSSOMPropertyResolver parentPropertyResolver, CSSRuleList componentRules) {
		Collections.sort(matchingCSSOMNodes, NODE_COMPARATOR);

		List<CSSRuleList> matchingRules = new ArrayList<>(matchingCSSOMNodes.size());
		matchingRules.add(componentRules);
		for (CSSOMNode<DocumentStyleGenerator, CSSRuleList> cssomNode: matchingCSSOMNodes) {
			matchingRules.addAll(cssomNode.getNodeProperties());
		}

		CSSOMPropertyResolver propertyResolver = CSSOMPropertyResolver.create(
			parentPropertyResolver, matchingRules.toArray(CSSRuleList[]::new));
		
		this.propertyResolver = propertyResolver;
		this.styleDirectives = new DocumentDirectivePool(
			parent == null ? null : parent.getStyleDirectives(),
			propertyResolver, declarationParser);
	}

	public Collection<CSSOMNode<DocumentStyleGenerator, CSSRuleList>> getMatchingCSSOMNodes() {
		return this.matchingCSSOMNodes;
	}

	public void addMatchingNode(CSSOMNode<DocumentStyleGenerator, CSSRuleList> baseNode) {
		this.matchingCSSOMNodes.add(baseNode);
	}

	private void generateChildren() {
		for (Node child: node.getChildNodes()) {
			children.add(new DocumentStyleGenerator(child, this, declarationParser));
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
