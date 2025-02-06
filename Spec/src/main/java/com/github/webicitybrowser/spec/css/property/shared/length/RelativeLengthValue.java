package com.github.webicitybrowser.spec.css.property.shared.length;

public record RelativeLengthValue(float value, RelativeLengthUnit unit) implements LengthValue {
	
	public static enum RelativeLengthUnit {
		EM, EX, CAP, CH, IC, REM, LH, RLH, VW, VH, VI, VB, VMIN, VMAX
	}

	public static RelativeLengthValue of(Number value, RelativeLengthUnit unit) {
		return new RelativeLengthValue(value.floatValue(), unit);
	}

	public static RelativeLengthValue of(Number value, String unit) {
		return of(value, RelativeLengthUnit.valueOf(unit.toUpperCase()));
	}

}
