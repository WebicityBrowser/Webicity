package com.github.webicitybrowser.spec.css.property.shared.length;

public record AbsoluteLengthValue(float value, AbsoluteLengthUnit unit) implements LengthValue {
	
	public static enum AbsoluteLengthUnit {
		CM, MM, Q, IN, PC, PT, PX
	}

	public static AbsoluteLengthValue of(Number value, AbsoluteLengthUnit unit) {
		return new AbsoluteLengthValue(value.floatValue(), unit);
	}

	public static AbsoluteLengthValue of(Number value, String unit) {
		return of(value, AbsoluteLengthUnit.valueOf(unit.toUpperCase()));
	}

}
