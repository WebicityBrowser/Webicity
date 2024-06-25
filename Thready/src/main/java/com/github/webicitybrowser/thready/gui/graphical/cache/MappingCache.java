package com.github.webicitybrowser.thready.gui.graphical.cache;

import java.util.List;
import java.util.function.Function;

public interface MappingCache<T, U> {

	void recompute(List<T> children, Function<T, U> mappingGenerator);

	List<U> getComputedMappings();
	
}