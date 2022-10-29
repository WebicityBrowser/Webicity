package everyos.web.spec.css.selectors;

public interface ComplexSelector extends ComplexSelectorPart {

	ComplexSelectorPart[] getParts();
	
	int[] getSpecificity();
	
}
