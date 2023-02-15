package everyos.browser.webicity.threadygui.renderer.html.cssom.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import everyos.browser.webicity.threadygui.renderer.html.component.WebComponent;
import everyos.browser.webicity.threadygui.renderer.html.cssom.CSSOMFilter;
import everyos.browser.webicity.threadygui.renderer.html.cssom.CSSOMNode;
import everyos.desktop.thready.basic.directive.pool.BasicDirectivePool;
import everyos.desktop.thready.core.gui.directive.DirectivePool;

public class CSSOMNodeImp implements CSSOMNode {
	
	// TODO: Quick paths
	private final Map<CSSOMFilter, CSSOMNode> childNodes = new HashMap<>();
	private final DirectivePool directives = new BasicDirectivePool();
	
	@Override
	public CSSOMNode getChild(CSSOMFilter filter) {
		return childNodes.computeIfAbsent(filter, _1 -> new CSSOMNodeImp());
	}
	
	@Override
	public CSSOMNode[] getApplicableNodes(WebComponent component, int position) {
		List<CSSOMNode> applicableNodes = new ArrayList<>();
		for (Entry<CSSOMFilter, CSSOMNode> childEntry: childNodes.entrySet()) {
			if (childEntry.getKey().isApplicable(component, position)) {
				applicableNodes.add(null);
			}
		}
		return applicableNodes.toArray(new CSSOMNode[0]);
	}

	@Override
	public DirectivePool getDirectivePool() {
		return this.directives;
	}
	
}
