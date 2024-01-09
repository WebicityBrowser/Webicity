package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp;

import java.util.List;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.thready.gui.directive.core.Directive;

public interface CSSOMNamedDeclarationParser<T extends CSSValue> {

	PropertyValueParser<T> getPropertyValueParser();
	
	Directive[] translatePropertyValue(T value);

	List<Class<? extends Directive>> getResultantDirectiveClasses();
	
}
