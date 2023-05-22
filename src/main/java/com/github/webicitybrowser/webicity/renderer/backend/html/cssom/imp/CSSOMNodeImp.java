package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.webicitybrowser.spec.css.selectors.SelectorSpecificity;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMComposableFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilterComposition;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilterEntry;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;

public class CSSOMNodeImp<T, U> implements CSSOMNode<T, U> {

	private final Map<CSSOMFilter<T, U>, CSSOMFilterEntry<T, U>> filters = new HashMap<>(4);
	private final List<CSSOMFilterEntry<T, U>> normalFilters = new ArrayList<>(0);
	private final List<CSSOMFilterEntry<T, U>> links = new ArrayList<>(0);
	private final Map<Class<?>, CSSOMFilterComposition<T, U, ?>> composableFilters = new HashMap<>(2);
	private final List<U> allProperties = new ArrayList<>(1);
	
	private SelectorSpecificity specificity;
	
	@Override
	public CSSOMNode<T, U> createChild(CSSOMFilter<T, U> filter, int staging) {
		return filters
			.computeIfAbsent(filter, key -> addCSSOMFilter(filter, staging))
			.cssomNode();
	}

	@Override
	public void linkChild(CSSOMFilter<T, U> filter, int staging, CSSOMNode<T, U> linkedNode) {
		links.add(new CSSOMFilterEntry<>(staging, filter, linkedNode));
	}
	
	@Override
	public List<CSSOMFilterEntry<T, U>> getPossibleFilters(T item) {
		List<CSSOMFilterEntry<T, U>> entries = new ArrayList<>(filters.size() + links.size());
		entries.addAll(normalFilters);
		entries.addAll(links);
		for (CSSOMFilterComposition<T, U, ?> composition: composableFilters.values()) {
			entries.addAll(composition.getPossibleFilters(item));
		}
		
		return entries;
	}

	@Override
	public void addNodeProperties(U properties) {
		allProperties.add(properties);
	}

	@Override
	public List<U> getNodeProperties() {
		return allProperties;
	}
	
	@Override
	public void setSpecificity(SelectorSpecificity specificity) {
		this.specificity = specificity;
	}

	@Override
	public SelectorSpecificity getSpecificity() {
		return this.specificity;
	}
	
	private <V extends CSSOMComposableFilter<T, U, V>> CSSOMFilterEntry<T, U> addCSSOMFilter(CSSOMFilter<T, U> filter, int staging) {
		CSSOMFilterEntry<T, U> filterEntry = new CSSOMFilterEntry<>(staging, filter, new CSSOMNodeImp<T, U>());
		if (filter instanceof CSSOMComposableFilter<T, U, ?> composableFilter) {
			composableFilters
				.computeIfAbsent(composableFilter.getFilterType(), _1 -> composableFilter.createFilterComposition())
				.addFilter(filterEntry);
		} else {
			normalFilters.add(filterEntry);
		}
		return filterEntry;
	}

}
