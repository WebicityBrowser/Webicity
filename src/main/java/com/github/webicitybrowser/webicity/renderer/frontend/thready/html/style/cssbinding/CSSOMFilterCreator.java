package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding;

import com.github.webicitybrowser.spec.css.selectors.ComplexSelectorPart;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssom.CSSOMFilter;

public interface CSSOMFilterCreator {

	CSSOMFilter createFilterFor(ComplexSelectorPart complexSelectorPart);

}
