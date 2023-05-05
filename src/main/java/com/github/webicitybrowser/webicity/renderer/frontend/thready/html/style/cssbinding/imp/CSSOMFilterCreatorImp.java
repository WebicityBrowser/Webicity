package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp;

import com.github.webicitybrowser.spec.css.QualifiedName;
import com.github.webicitybrowser.spec.css.selectors.ComplexSelectorPart;
import com.github.webicitybrowser.spec.css.selectors.selector.TypeSelector;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.filter.TypeFilter;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMFilterCreator;

public class CSSOMFilterCreatorImp implements CSSOMFilterCreator {

	@Override
	public CSSOMFilter<Node, DirectivePool> createFilterFor(ComplexSelectorPart complexSelectorPart) {
		if (complexSelectorPart instanceof TypeSelector typeSelector) {
			QualifiedName name = typeSelector.getQualifiedName();
			return new TypeFilter<>(name.getNamespace(), name.getName());
		} else {
			throw new UnsupportedOperationException("Not implemented yet");
		}
	}

}
