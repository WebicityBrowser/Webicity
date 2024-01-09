package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMPropertyResolverImp;

/**
 * The CSSOMPropertyResolver chooses the correct property value for a given
 * property name, and resolves the long-form value of the property.
 */
public interface CSSOMPropertyResolver {
	
	/**
	 * Resolve the property value for the given property name.
	 * @param propertyName The name of the property to resolve
	 * @return The resolved property value
	 */
	TokenLike[] resolveProperty(String propertyName);

    static CSSOMPropertyResolver create(CSSOMPropertyResolver parentPropertyResolver, CSSOMRuleMap[] matchingRules) {
        return new CSSOMPropertyResolverImp(parentPropertyResolver, matchingRules);
    }

}
