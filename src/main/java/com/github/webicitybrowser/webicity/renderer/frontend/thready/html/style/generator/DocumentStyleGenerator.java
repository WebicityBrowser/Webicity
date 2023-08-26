package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.generator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.thready.gui.directive.basics.pool.NestingDirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.pool.ComposedDirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.threadyweb.tree.WebComponent;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;

public class DocumentStyleGenerator implements StyleGenerator {
	
	private static final NodeComparator NODE_COMPARATOR = new NodeComparator();

	private final Node node;
	private final List<CSSOMNode<DocumentStyleGenerator, DirectivePool>> matchingCSSOMNodes = new ArrayList<>(1);
	private final DocumentStyleGenerator parent;
	private final List<DocumentStyleGenerator> children = new ArrayList<>(1);

	private DirectivePool styleDirectives;

	public DocumentStyleGenerator(Node node, DocumentStyleGenerator parent) {
		this.node = node;
		this.parent = parent;
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
			childGenerator.generateStyleDirectives(this.styleDirectives, childComponent.getStyleDirectives());
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

	public void generateStyleDirectives(DirectivePool parentPool, DirectivePool componentPool) {
		Collections.sort(matchingCSSOMNodes, NODE_COMPARATOR);

		List<DirectivePool> matchingPools = new ArrayList<>(matchingCSSOMNodes.size());
		for (CSSOMNode<DocumentStyleGenerator, DirectivePool> cssomNode: matchingCSSOMNodes) {
			matchingPools.addAll(cssomNode.getNodeProperties());
		}
		
		ComposedDirectivePool<DirectivePool> combinedPool = new NestingDirectivePool(parentPool);
		combinedPool.addDirectivePool(componentPool);
		for (DirectivePool pool: matchingPools) {
			combinedPool.addDirectivePool(pool);
		}
		
		this.styleDirectives = combinedPool;
	}

	public Collection<CSSOMNode<DocumentStyleGenerator, DirectivePool>> getMatchingCSSOMNodes() {
		return this.matchingCSSOMNodes;
	}

	public void addMatchingNode(CSSOMNode<DocumentStyleGenerator, DirectivePool> baseNode) {
		this.matchingCSSOMNodes.add(baseNode);
	}

	private void generateChildren() {
		for (Node child: node.getChildNodes()) {
			children.add(new DocumentStyleGenerator(child, this));
		}
	}

	private static class NodeComparator implements Comparator<CSSOMNode<DocumentStyleGenerator, DirectivePool>> {
		@Override
		public int compare(CSSOMNode<DocumentStyleGenerator, DirectivePool> o1, CSSOMNode<DocumentStyleGenerator, DirectivePool> o2) {
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
