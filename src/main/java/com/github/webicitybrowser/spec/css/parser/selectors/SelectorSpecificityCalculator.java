package com.github.webicitybrowser.spec.css.parser.selectors;

import java.util.function.Function;

import com.github.webicitybrowser.spec.css.selectors.ComplexSelectorPart;
import com.github.webicitybrowser.spec.css.selectors.SelectorSpecificity;
import com.github.webicitybrowser.spec.css.selectors.selector.AttributeSelector;
import com.github.webicitybrowser.spec.css.selectors.selector.IDSelector;
import com.github.webicitybrowser.spec.css.selectors.selector.TypeSelector;

public final class SelectorSpecificityCalculator {

	private SelectorSpecificityCalculator() {}
	
	public static SelectorSpecificity calculateSpecificity(ComplexSelectorPart[] parts, int order) {
		int idCount = countSelectors(parts, selector -> selector instanceof IDSelector);
		int attributeCount = countSelectors(parts, selector -> selector instanceof AttributeSelector);
		int typeCount = countSelectors(parts, selector -> selector instanceof TypeSelector);
		
		return new SelectorSpecificity(idCount, attributeCount, typeCount, order);
	}

	private static int countSelectors(ComplexSelectorPart[] parts, Function<ComplexSelectorPart, Boolean> filter) {
		int selectorCount = 0;
		for (ComplexSelectorPart part: parts) {
			if (filter.apply(part)) {
				selectorCount++;
			}
		}
		
		return selectorCount;
	}

}
