package com.github.webicitybrowser.spec.css.parser.property.imp;

import java.util.Optional;

import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.property.CSSValue;

public class PropertyValueParseResultImp<T extends CSSValue> implements PropertyValueParseResult<T> {

	private final T value;
	private final int length;

	private PropertyValueParseResultImp(T value, int length) {
		this.value = value;
		this.length = length;
	}

	@Override
	public Optional<T> getResult() {
		return Optional.ofNullable(value);
	}

	@Override
	public int getLength() {
		return length;
	}

	@Override
	public <U extends CSSValue> PropertyValueParseResult<U> as(Class<U> cls) {
		return new PropertyValueParseResultImp<>(cls.cast(value), length);
	}
	
	public static <T extends CSSValue> PropertyValueParseResult<T> of(T value, int length) {
		return new PropertyValueParseResultImp<>(value, length);
	}
	
	public static <T extends CSSValue> PropertyValueParseResult<T> empty() {
		return new PropertyValueParseResultImp<>(null, 0);
	}
	
}
