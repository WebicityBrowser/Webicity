package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.generator;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.gui.directive.core.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.threadyweb.tree.WebComponent;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssom.CSSOMNode;

public class DocumentStyleGenerator implements StyleGenerator {
	
	private final CSSOMNode[] cssomNodes;

	public DocumentStyleGenerator(CSSOMNode[] cssomNodes) {
		this.cssomNodes = cssomNodes;
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
		List<DirectivePool> pools = new ArrayList<>(cssomNodes.length);
		for (int i = 0; i < cssomNodes.length; i++) {
			pools.addAll(cssomNodes[i].getDirectivePools());
		}
		
		return pools.toArray(DirectivePool[]::new);
	}
	
	private StyleGenerator createChildGenerator(ComponentUI childUI, int i) {
		WebComponent childComponent = (WebComponent) childUI.getComponent();
		List<CSSOMNode> childCSSOMNodes = new ArrayList<>();
		
		addRootCSSOMNode(childCSSOMNodes);
		addChildCSSOMNodes(childCSSOMNodes, childComponent, i);
		
		return new DocumentStyleGenerator(childCSSOMNodes.toArray(CSSOMNode[]::new));
	}

	private void addRootCSSOMNode(List<CSSOMNode> childCSSOMNodes) {
		CSSOMNode rootNode = cssomNodes[0];
		childCSSOMNodes.add(rootNode);
	}
	
	private void addChildCSSOMNodes(List<CSSOMNode> childCSSOMNodes, WebComponent childComponent, int i) {
		for (CSSOMNode currentNode: cssomNodes) {
			CSSOMNode[] matchedNodes = currentNode.getApplicableNodes(childComponent, i);
			for (CSSOMNode matchedNode: matchedNodes) {
				childCSSOMNodes.add(matchedNode);
			}
		}
	}

}
