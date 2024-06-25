package com.github.webicitybrowser.webicity.renderer.backend.html.cssom;

import com.github.webicitybrowser.spec.css.rule.CSSRuleList;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.imp.CSSOMTreeGeneratorImp;

/**
 * Implementations of this interface can create a {@link CSSOMTree} from a {@link CSSRuleList}.
 * A flat list of rules is transformed into a tree of properties.
 */
public interface CSSOMTreeGenerator<T> {

	/**
	 * Creates a {@link CSSOMTree} from a {@link CSSRuleList}.
	 * @param ruleList the {@link CSSRuleList} to create the {@link CSSOMTree} from
	 * @return the created {@link CSSOMTree}
	 */
	CSSOMTree<T, CSSRuleList> createCSSOMFor(CSSRuleList ruleList);
	
	/**
	 * Creates a {@link CSSOMTreeGenerator} using the given {@link CSSOMFilterCreator}
	 * to implement the logic used for selectors.
	 * @param filterCreator the {@link CSSOMFilterCreator} to use
	 * @return the created {@link CSSOMTreeGenerator}
	 */
	static <T> CSSOMTreeGenerator<T> create(CSSOMFilterCreator<T> filterCreator) {
		return new CSSOMTreeGeneratorImp<>(filterCreator);
	}
	
}
