package com.github.webicitybrowser.spec.css.parser.property;

import java.util.Optional;

import com.github.webicitybrowser.spec.css.property.CSSValue;

public interface PropertyValueParseResult<T extends CSSValue> {

	Optional<T> getResult();
	
	int getLength();

	<U extends CSSValue> PropertyValueParseResult<U> as(Class<U> cls);
	
}
