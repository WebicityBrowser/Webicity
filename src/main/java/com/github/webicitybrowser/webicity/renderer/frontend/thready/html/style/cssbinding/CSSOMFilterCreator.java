package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding;

import com.github.webicitybrowser.spec.css.selectors.ComplexSelectorPart;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilter;

public interface CSSOMFilterCreator {

	CSSOMFilter<Node, DirectivePool> createFilterFor(ComplexSelectorPart complexSelectorPart);

}
