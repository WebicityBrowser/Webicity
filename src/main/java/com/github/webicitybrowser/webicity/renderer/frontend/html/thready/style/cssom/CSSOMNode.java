package com.github.webicitybrowser.webicity.renderer.frontend.html.thready.style.cssom;

import java.util.List;

import com.github.webicitybrowser.thready.gui.directive.core.DirectivePool;
import com.github.webicitybrowser.threadyweb.tree.WebComponent;
import com.github.webicitybrowser.webicity.renderer.frontend.html.thready.style.cssom.imp.CSSOMNodeImp;

public interface CSSOMNode {
	
	CSSOMNode getChild(CSSOMFilter filter);

	CSSOMNode[] getApplicableNodes(WebComponent component, int position);

	void addDirectivePool(DirectivePool directivePool);
	
	List<DirectivePool> getDirectivePools();
	
	public static CSSOMNode create() {
		return new CSSOMNodeImp();
	}

}
