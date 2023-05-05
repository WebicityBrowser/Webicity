package com.github.webicitybrowser.webicity.renderer.backend.html.cssom.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilter;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMFilterEntry;
import com.github.webicitybrowser.webicity.renderer.backend.html.cssom.CSSOMNode;

public class CSSOMNodeImp<T, U> implements CSSOMNode<T, U> {

	private final Map<CSSOMFilter<T, U>, CSSOMFilterEntry<T, U>> filters = new HashMap<>(4);
	private final List<CSSOMFilterEntry<T, U>> links = new ArrayList<>(0);
	private final List<U> allProperties = new ArrayList<>(1);
	
	@Override
	public CSSOMNode<T, U> createChild(CSSOMFilter<T, U> filter, int staging) {
		return filters
			.computeIfAbsent(filter, key -> new CSSOMFilterEntry<>(staging, filter, new CSSOMNodeImp<T, U>()))
			.cssomNode();
	}
	
	@Override
	public void linkChild(CSSOMFilter<T, U> filter, int staging, CSSOMNode<T, U> linkedNode) {
		links.add(new CSSOMFilterEntry<>(staging, filter, linkedNode));
	}
	
	@Override
	public List<CSSOMFilterEntry<T, U>> getFilters() {
		List<CSSOMFilterEntry<T, U>> entries = new ArrayList<>(filters.size() + links.size());
		entries.addAll(filters.values());
		entries.addAll(links);
		
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

}
