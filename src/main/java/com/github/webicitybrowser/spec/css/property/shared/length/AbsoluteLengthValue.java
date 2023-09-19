package com.github.webicitybrowser.spec.css.property.shared.length;

public interface AbsoluteLengthValue extends LengthValue {
	
	AbsoluteLengthUnit getUnit();

	float getValue();
	
	public static enum AbsoluteLengthUnit {
		CM, MM, Q, IN, PC, PT, PX
	}

	static AbsoluteLengthValue of(Number value, AbsoluteLengthUnit unit) {
		return new AbsoluteLengthValue() {
			@Override
			public AbsoluteLengthUnit getUnit() {
				return unit;
			}

			@Override
			public float getValue() {
				return value.floatValue();
			}
		};
	}

	static AbsoluteLengthValue of(Number value, String unit) {
		return of(value, AbsoluteLengthUnit.valueOf(unit.toUpperCase()));
	}

}
