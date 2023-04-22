package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;

public interface CSSOMNamedDeclarationParser<T> {

	PropertyValueParser<T> getPropertyValueParser();
	
	Directive[] translatePropertyValue(T value);
	
}
