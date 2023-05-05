package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.generator;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.threadyweb.tree.WebComponent;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMResult;

public class DocumentStyleGenerator implements StyleGenerator {
	
	private final Node node;
	private final CSSOMResult<Node, DirectivePool>[] cssomResults;

	public DocumentStyleGenerator(Node node, CSSOMResult<Node, DirectivePool>[] results) {
		this.node = node;
		this.cssomResults = results;
	}

	@Override
	public StyleGenerator[] createChildStyleGenerators(ComponentUI[] children) {
		StyleGenerator[] childGenerators = new StyleGenerator[children.length];
		for (int i = 0; i < children.length; i++) {
			childGenerators[i] = createChildGenerator(children[i], i);
		}
		
		return childGenerators;
	}

	@Override
	public DirectivePool[] getDirectivePools() {
		List<DirectivePool> pools = new ArrayList<>(cssomResults.length);
		for (CSSOMResult<Node, DirectivePool> result: cssomResults) {
			for (CSSOMNode<Node, DirectivePool> node: result.getMatchingNodes(node)) {
				pools.addAll(node.getNodeProperties());
			}
		}
		
		return pools.toArray(DirectivePool[]::new);
	}
	
	private StyleGenerator createChildGenerator(ComponentUI childUI, int i) {
		return new DocumentStyleGenerator(((WebComponent) childUI.getComponent()).getNode(), cssomResults);
	}

}
