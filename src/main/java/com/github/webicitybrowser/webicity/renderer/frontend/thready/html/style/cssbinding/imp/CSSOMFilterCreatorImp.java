package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp;

import java.util.function.Function;

import com.github.webicitybrowser.spec.css.QualifiedName;
import com.github.webicitybrowser.spec.css.selectors.ComplexSelectorPart;
import com.github.webicitybrowser.spec.css.selectors.combinator.ChildCombinator;
import com.github.webicitybrowser.spec.css.selectors.combinator.DescendantCombinator;
import com.github.webicitybrowser.spec.css.selectors.combinator.NextSiblingCombinator;
import com.github.webicitybrowser.spec.css.selectors.combinator.SubsequentSiblingCombinator;
import com.github.webicitybrowser.spec.css.selectors.selector.AttributeSelector;
import com.github.webicitybrowser.spec.css.selectors.selector.AttributeSelector.AttributeSelectorOperation;
import com.github.webicitybrowser.spec.css.selectors.selector.IDSelector;
import com.github.webicitybrowser.spec.css.selectors.selector.TypeSelector;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.filter.AttributeFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.filter.ChildFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.filter.DescendantFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.filter.NextSiblingFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.filter.SubsequentSiblingFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.filter.attroneof.AttributeOneOfFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.filter.id.IDFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.filter.type.TypeFilter;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMFilterCreator;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMRuleMap;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.generator.DocumentStyleGenerator;

public class CSSOMFilterCreatorImp implements CSSOMFilterCreator {

	private static final Function<DocumentStyleGenerator, Node> NODE_GETTER = item -> item.getDOMNode();

	@Override
	public CSSOMFilter<DocumentStyleGenerator, CSSOMRuleMap> createFilterFor(ComplexSelectorPart complexSelectorPart) {
		if (complexSelectorPart instanceof TypeSelector typeSelector) {
			QualifiedName name = typeSelector.getQualifiedName();
			return new TypeFilter<>(name.getNamespace(), name.getName(), NODE_GETTER);
		} else if (isAttributeSelector(complexSelectorPart, AttributeSelectorOperation.ONE_OF)) {
			return new AttributeOneOfFilter<>((AttributeSelector) complexSelectorPart, NODE_GETTER);
		} else if (complexSelectorPart instanceof AttributeSelector attributeSelector) {
			return new AttributeFilter<>(attributeSelector, NODE_GETTER);
		} else if (complexSelectorPart instanceof IDSelector idSelector) {
			return new IDFilter<>(idSelector.getId(), NODE_GETTER);
		} else if (complexSelectorPart instanceof ChildCombinator) {
			return new ChildFilter<>();
		} else if (complexSelectorPart instanceof DescendantCombinator) {
			return new DescendantFilter<>();
		} else if (complexSelectorPart instanceof SubsequentSiblingCombinator) {
			return new SubsequentSiblingFilter<>();
		} else if (complexSelectorPart instanceof NextSiblingCombinator) {
			return new NextSiblingFilter<>();
		} else {
			throw new UnsupportedOperationException("Not implemented yet: " +
				complexSelectorPart.getClass().getInterfaces()[0].getName());
		}
	}

	private boolean isAttributeSelector(ComplexSelectorPart selector, AttributeSelectorOperation operation) {
		return
			selector instanceof AttributeSelector attributeSelector &&
			attributeSelector.getOperation() == operation;
	}

}
