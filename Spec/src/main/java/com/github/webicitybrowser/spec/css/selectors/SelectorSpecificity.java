package com.github.webicitybrowser.spec.css.selectors;

public record SelectorSpecificity(Source source, int idSelectors, int attributeSelectors, int typeSelectors, int documentOrder, int order) implements Comparable<SelectorSpecificity> {

	@Override
	public int compareTo(SelectorSpecificity specificity) {
		if (source != specificity.source()) {
			return source.ordinal() > specificity.source().ordinal() ? 1 : -1;
		} else if (idSelectors != specificity.idSelectors()) {
			return idSelectors > specificity.idSelectors() ? 1 : -1;
		} else if (attributeSelectors != specificity.attributeSelectors()) {
			return attributeSelectors > specificity.attributeSelectors() ? 1 : -1;
		} else if (typeSelectors != specificity.typeSelectors()) {
			return typeSelectors > specificity.typeSelectors() ? 1 : -1;
		} else if (documentOrder != specificity.documentOrder()) {
			return documentOrder > specificity.documentOrder() ? 1 : -1;
		} else if (order != specificity.order()) {
			return order > specificity.order() ? 1 : -1;
		} else {
			return 0;
		}
	}

	public static enum Source {
		UA, USER, AUTHOR
	}

}
