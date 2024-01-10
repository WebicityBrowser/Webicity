package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding;

import java.util.Optional;

import com.github.webicitybrowser.spec.css.rule.CSSRuleList;
import com.github.webicitybrowser.spec.css.rule.Declaration;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.CSSOMPropertyResolverImp;

/**
 * The CSSOMPropertyResolver chooses the correct property value for a given
 * property name, and resolves the long-form value of the property.
 */
public interface CSSOMPropertyResolver {
	
	/**
	 * Given a filter which checks if a given property is applicable valid and, if so,
	 * converts the property to a usable application-specific value, this method
	 * attempts to find the most applicable property value that fulfills the filter's
	 * requirements while also abiding by the rules of CSS.
	 * @param <T> The type of application-specific value to convert the property to
	 * @param filter The filter to check if a given property is applicable and convert it
	 *  to a usable application-specific value
	 * @return An optional containing the application-specific value of the property if
	 *  the filter was able to find a valid property, or an empty optional if no valid
	 *  property was found
	 */
	<T> Optional<T> resolveProperty(CSSOMPropertyResolverFilter<T> filter);

    static CSSOMPropertyResolver create(CSSOMPropertyResolver parentPropertyResolver, CSSRuleList[] matchingRules) {
        return new CSSOMPropertyResolverImp(parentPropertyResolver, matchingRules);
    }

	interface CSSOMPropertyResolverFilter<T> {
		Optional<T> filter(Declaration propertyValue);
	}

}
