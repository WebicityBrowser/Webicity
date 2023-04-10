package com.github.webicitybrowser.thready.gui.graphical.cache;

import java.util.function.Function;

public interface MappingCache<T, U> {

	void recompute(T[] children, Function<T, U> mappingGenerator);

	U[] getComputedMappings();
	
}