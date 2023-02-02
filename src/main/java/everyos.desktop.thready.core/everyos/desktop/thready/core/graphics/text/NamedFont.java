package everyos.desktop.thready.core.graphics.text;

import java.util.Objects;

public class NamedFont implements Font {

	private final String name;

	public NamedFont(String name) {
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
			(o instanceof NamedFont) &&
			((NamedFont) o).getName().equals(name);
	}
	
}
