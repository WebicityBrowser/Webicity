package everyos.browser.jcss.cssom.selector;

import java.util.Objects;

import everyos.browser.jcss.cssom.SelectorPart;

public class TypeSelectorPart implements SelectorPart {

	private String name;

	public TypeSelectorPart(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof TypeSelectorPart)) {
			return false;
		}
		
		return ((TypeSelectorPart) o).getName() == getName();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}
