package com.github.webicitybrowser.thready.drawing.core.text.source;

import java.util.Objects;

public class NamedFontSource implements FontSource {

	private final String name;

	public NamedFontSource(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
	
	@Override
	public boolean equals(Object o) {
		return
			(o instanceof NamedFontSource) &&
			((NamedFontSource) o).getName().equals(name);
	}
	
}