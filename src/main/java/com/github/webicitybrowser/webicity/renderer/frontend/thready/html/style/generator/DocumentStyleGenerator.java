package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.generator;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.thready.gui.directive.basics.pool.NestingDirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.pool.ComposedDirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.threadyweb.tree.WebComponent;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMResult;

public class DocumentStyleGenerator implements StyleGenerator {
	
	private final WebComponent component;
	private final CSSOMResult<Node, DirectivePool>[] cssomResults;
	private final DirectivePool styleDirectives;

	public DocumentStyleGenerator(WebComponent component, CSSOMResult<Node, DirectivePool>[] cssomResults, DirectivePool parentPool) {
		this.component = component;
		this.cssomResults = cssomResults;
		this.styleDirectives = generateStyleDirectives(parentPool);
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
	public DirectivePool getStyleDirectives() {
		return this.styleDirectives;
	}

	public DirectivePool generateStyleDirectives(DirectivePool parentPool) {
		List<DirectivePool> matchingPools = new ArrayList<>(cssomResults.length);
		for (CSSOMResult<Node, DirectivePool> result: cssomResults) {
			for (CSSOMNode<Node, DirectivePool> node: result.getMatchingNodes(component.getNode())) {
				matchingPools.addAll(node.getNodeProperties());
			}
		}
		
		ComposedDirectivePool<DirectivePool> combinedPool = new NestingDirectivePool(parentPool);
		combinedPool.addDirectivePool(component.getStyleDirectives());
		for (DirectivePool pool: matchingPools) {
			combinedPool.addDirectivePool(pool);
		}
		
		return combinedPool;
	}
	
	private StyleGenerator createChildGenerator(ComponentUI childUI, int i) {
		return new DocumentStyleGenerator((WebComponent) childUI.getComponent(), cssomResults, styleDirectives);
	}

}
