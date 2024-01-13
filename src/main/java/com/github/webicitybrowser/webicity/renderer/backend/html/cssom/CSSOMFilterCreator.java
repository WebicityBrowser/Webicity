package com.github.webicitybrowser.webicity.renderer.backend.html.cssom;

import java.util.function.Function;

import com.github.webicitybrowser.spec.css.rule.CSSRuleList;
import com.github.webicitybrowser.spec.css.selectors.ComplexSelectorPart;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.imp.CSSOMFilterCreatorImp;

public interface CSSOMFilterCreator<T> {

	CSSOMFilter<T, CSSRuleList> createFilterFor(ComplexSelectorPart complexSelectorPart);

	static <T> CSSOMFilterCreator<T> create(Function<T, Node> nodeGetter) {
		return new CSSOMFilterCreatorImp<>(nodeGetter);
	}

}
