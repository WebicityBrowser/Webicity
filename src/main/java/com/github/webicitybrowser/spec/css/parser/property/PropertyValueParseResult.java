package com.github.webicitybrowser.spec.css.parser.property;

import java.util.Optional;

public interface PropertyValueParseResult<T> {

	Optional<T> getResult();
	
	int getLength();
	
}
