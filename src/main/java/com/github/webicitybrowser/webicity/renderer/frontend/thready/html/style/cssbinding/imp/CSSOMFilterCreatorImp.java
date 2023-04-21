package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp;

import com.github.webicitybrowser.spec.css.QualifiedName;
import com.github.webicitybrowser.spec.css.selectors.ComplexSelectorPart;
import com.github.webicitybrowser.spec.css.selectors.selector.TypeSelector;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.CSSOMFilterCreator;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssom.CSSOMFilter;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssom.filters.TypeFilter;

public class CSSOMFilterCreatorImp implements CSSOMFilterCreator {

	@Override
	public CSSOMFilter createFilterFor(ComplexSelectorPart complexSelectorPart) {
		System.out.println(complexSelectorPart);
		if (complexSelectorPart instanceof TypeSelector typeSelector) {
			QualifiedName name = typeSelector.getQualifiedName();
			return new TypeFilter(name.getNamespace(), name.getName());
		} else {
			throw new UnsupportedOperationException("Not implemented yet");
		}
	}

}
