package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding;

import com.github.webicitybrowser.spec.css.rule.CSSStyleSheet;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMTree;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMBinderImp;

public interface CSSOMBinder {

	CSSOMTree<Node, DirectivePool> createCSSOMFor(CSSStyleSheet stylesheet);
	
	public static CSSOMBinder create() {
		return new CSSOMBinderImp();
	}
	
}
