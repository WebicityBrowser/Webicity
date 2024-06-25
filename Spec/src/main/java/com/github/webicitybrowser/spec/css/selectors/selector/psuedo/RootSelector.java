package com.github.webicitybrowser.spec.css.selectors.selector.psuedo;

public interface RootSelector extends PsuedoSelector {
	
	@Override
	default String getType() {
		return "root";
	}

}
