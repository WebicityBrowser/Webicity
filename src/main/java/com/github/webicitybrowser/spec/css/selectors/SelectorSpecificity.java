package com.github.webicitybrowser.spec.css.selectors;

public record SelectorSpecificity(int idSelectors, int attributeSelectors, int typeSelectors, int order) implements Comparable<SelectorSpecificity> {

	@Override
	public int compareTo(SelectorSpecificity specificity) {
		if (idSelectors != specificity.idSelectors()) {
			return idSelectors > specificity.idSelectors() ? 1 : -1;
		} else if (attributeSelectors != specificity.attributeSelectors()) {
			return attributeSelectors > specificity.attributeSelectors() ? 1 : -1;
		} else if (typeSelectors != specificity.typeSelectors()) {
			return typeSelectors > specificity.typeSelectors() ? 1 : -1;
		} else if (order != specificity.order()) {
			return order > specificity.order() ? 1 : -1;
		} else {
			return 1;
		}
	}

}
