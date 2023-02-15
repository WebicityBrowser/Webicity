package everyos.browser.webicity.threadygui.renderer.html.styling;

import java.util.ArrayList;
import java.util.List;

import everyos.browser.webicity.threadygui.renderer.html.component.WebComponent;
import everyos.browser.webicity.threadygui.renderer.html.cssom.CSSOMNode;
import everyos.desktop.thready.core.gui.directive.DirectivePool;
import everyos.desktop.thready.core.gui.directive.style.StyleGenerator;
import everyos.desktop.thready.core.gui.laf.ComponentUI;

public class DocumentStyleGenerator implements StyleGenerator {
	
	private CSSOMNode[] cssomNodes;

	public DocumentStyleGenerator(CSSOMNode[] cssomNodes) {
		this.cssomNodes = cssomNodes;
	}

	@Override
	public StyleGenerator[] createChildStyleGenerators(ComponentUI[] children) {
		StyleGenerator[] childGenerators = new StyleGenerator[children.length + 1];
		for (int i = 0; i < children.length; i++) {
			childGenerators[i + 1] = createChildGenerator(children[i], i);
		}
		
		return childGenerators;
	}

	@Override
	public DirectivePool[] getDirectivePools() {
		DirectivePool[] pools = new DirectivePool[cssomNodes.length];
		for (int i = 0; i < cssomNodes.length; i++) {
			pools[i] = cssomNodes[i].getDirectivePool();
		}
		
		return pools;
	}
	
	private StyleGenerator createChildGenerator(ComponentUI childUI, int i) {
		WebComponent childComponent = (WebComponent) childUI.getComponent();
		List<CSSOMNode> childCSSOMNodes = new ArrayList<>();
		
		addRootCSSOMNode(childCSSOMNodes);
		addChildCSSOMNodes(childCSSOMNodes, childComponent, i);
		
		return new DocumentStyleGenerator(childCSSOMNodes.toArray(new CSSOMNode[0]));
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
