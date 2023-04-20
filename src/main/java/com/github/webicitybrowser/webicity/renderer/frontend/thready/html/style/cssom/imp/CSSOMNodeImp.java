package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssom.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.github.webicitybrowser.thready.gui.directive.core.DirectivePool;
import com.github.webicitybrowser.threadyweb.tree.WebComponent;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssom.CSSOMFilter;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssom.CSSOMNode;

public class CSSOMNodeImp implements CSSOMNode {
	
	// TODO: Quick paths
	private final Map<CSSOMFilter, CSSOMNode> childNodes = new HashMap<>();
	private final List<DirectivePool> directivePools = new ArrayList<>();
	
	@Override
	public CSSOMNode getChild(CSSOMFilter filter) {
		return childNodes.computeIfAbsent(filter, _1 -> new CSSOMNodeImp());
	}
	
	@Override
	public CSSOMNode[] getApplicableNodes(WebComponent component, int position) {
		List<CSSOMNode> applicableNodes = new ArrayList<>();
		for (Entry<CSSOMFilter, CSSOMNode> childEntry: childNodes.entrySet()) {
			if (childEntry.getKey().isApplicable(component, position)) {
				applicableNodes.add(childEntry.getValue());
			}
		}
		return applicableNodes.toArray(new CSSOMNode[0]);
	}
	
	@Override
	public void addDirectivePool(DirectivePool directivePool) {
		directivePools.add(directivePool);
	}

	@Override
	public List<DirectivePool> getDirectivePools() {
		return List.copyOf(directivePools);
	}
	
}