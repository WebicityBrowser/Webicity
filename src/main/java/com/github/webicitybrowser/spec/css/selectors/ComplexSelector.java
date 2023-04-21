package com.github.webicitybrowser.spec.css.selectors;

public interface ComplexSelector extends ComplexSelectorPart {

	ComplexSelectorPart[] getParts();
	
	int getSpecificity();
	
}
