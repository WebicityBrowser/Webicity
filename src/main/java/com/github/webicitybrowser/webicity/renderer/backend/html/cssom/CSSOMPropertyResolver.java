package com.github.webicitybrowser.webicity.renderer.backend.html.cssom;

import java.util.Optional;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.rule.CSSRuleList;
import com.github.webicitybrowser.spec.css.rule.Declaration;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.imp.CSSOMPropertyResolverImp;

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

	/**
	 * Given a filter which checks if a given property is applicable valid and, if so,
	 * converts the property to a usable application-specific value, this method
	 * attempts to find the most applicable property value that fulfills the filter's
	 * requirements while also abiding by the rules of CSS. If no valid property is
	 * found, this method will repeat the process with each parent property resolver
	 * until a valid property is found or there are no more parent property resolvers.
	 * @param <T> The type of application-specific value to convert the property to
	 * @param filter The filter to check if a given property is applicable and convert it
	 *  to a usable application-specific value
	 * @return An optional containing the application-specific value of the property if
	 *  the filter was able to find a valid property, or an empty optional if no valid
	 *  property was found
	 */
	<T> Optional<T> resolveOrInheritProperty(CSSOMPropertyResolverFilter<T> filter);

    static CSSOMPropertyResolver create(CSSOMPropertyResolver parentPropertyResolver, CSSRuleList[] matchingRules) {
        return new CSSOMPropertyResolverImp(parentPropertyResolver, matchingRules);
    }

	interface CSSOMPropertyResolverFilter<T> {
		boolean isApplicable(Declaration propertyValue);
		Optional<T> filter(String name, TokenLike[] tokens);
	}

}
