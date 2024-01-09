package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding;

import com.github.webicitybrowser.spec.css.rule.Declaration;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMRuleMapImp;

/**
 * A CSSOMRuleMap maps from a property name to the declaration
 * responsible for the value of that property.
 */
public interface CSSOMRuleMap {

	/**
	 * Get the mapped declaration for the given property name.
	 * @param propertyName The name of the property to get the declaration for
	 * @return The declaration responsible for the value of the given property name
	 */
	Declaration getDeclaration(String propertyName);

	static CSSOMRuleMap create(Declaration[] declarations) {
		return new CSSOMRuleMapImp(declarations);
	}

	static CSSOMRuleMap createEmpty() {
		return new CSSOMRuleMapImp(new Declaration[0]);
	}

}
