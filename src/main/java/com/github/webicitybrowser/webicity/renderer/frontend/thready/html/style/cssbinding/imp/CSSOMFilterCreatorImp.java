package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp;

import com.github.webicitybrowser.spec.css.QualifiedName;
import com.github.webicitybrowser.spec.css.selectors.ComplexSelectorPart;
import com.github.webicitybrowser.spec.css.selectors.selector.AttributeSelector;
import com.github.webicitybrowser.spec.css.selectors.selector.AttributeSelector.AttributeSelectorOperation;
import com.github.webicitybrowser.spec.css.selectors.selector.TypeSelector;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.filter.AttributeOneOfFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.filter.TypeFilter;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMFilterCreator;

public class CSSOMFilterCreatorImp implements CSSOMFilterCreator {

	@Override
	public CSSOMFilter<Node, DirectivePool> createFilterFor(ComplexSelectorPart complexSelectorPart) {
		if (complexSelectorPart instanceof TypeSelector typeSelector) {
			QualifiedName name = typeSelector.getQualifiedName();
			return new TypeFilter<>(name.getNamespace(), name.getName());
		} else if (isAttributeSelector(complexSelectorPart, AttributeSelectorOperation.ONE_OF)) {
			return new AttributeOneOfFilter<>((AttributeSelector) complexSelectorPart);
		} else {
			throw new UnsupportedOperationException("Not implemented yet");
		}
	}

	private boolean isAttributeSelector(ComplexSelectorPart selector, AttributeSelectorOperation operation) {
		return
			selector instanceof AttributeSelector attributeSelector &&
			attributeSelector.getOperation() == operation;
	}

}
